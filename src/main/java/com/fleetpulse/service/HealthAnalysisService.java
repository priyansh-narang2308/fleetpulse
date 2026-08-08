package com.fleetpulse.service;

import com.fleetpulse.dto.HealthReport;
import com.fleetpulse.engine.HealthRule;
import com.fleetpulse.engine.RiskScoreCalculator;
import com.fleetpulse.engine.RiskScoreCalculator.CalculationResult;
import com.fleetpulse.engine.RuleResult;
import com.fleetpulse.entity.Machine;
import com.fleetpulse.entity.MaintenanceAlert;
import com.fleetpulse.entity.Telemetry;
import com.fleetpulse.exception.ResourceNotFoundException;
import com.fleetpulse.repository.MaintenanceAlertRepository;
import com.fleetpulse.repository.TelemetryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HealthAnalysisService {

    private final TelemetryRepository telemetryRepository;
    private final MaintenanceAlertRepository alertRepository;
    private final List<HealthRule> rules;
    private final RiskScoreCalculator riskScoreCalculator;
    private final MachineService machineService;

    @Transactional
    public HealthReport analyzeMachineHealth(Long machineId) {
        Machine machine = machineService.getMachineEntityById(machineId);

        Telemetry latestTelemetry = telemetryRepository.findTopByMachineIdOrderByRecordedAtDesc(machineId)
                .orElseThrow(() -> new ResourceNotFoundException("No telemetry data found for machine id: " + machineId));

        return analyze(machine, latestTelemetry, false);
    }

    @Transactional
    public HealthReport analyzeAndAlert(Machine machine, Telemetry telemetry) {
        return analyze(machine, telemetry, true);
    }

    private HealthReport analyze(Machine machine, Telemetry telemetry, boolean persistAlerts) {
        // Run all rules against the latest telemetry
        List<RuleResult> ruleResults = rules.stream()
                .map(rule -> rule.evaluate(telemetry))
                .collect(Collectors.toList());

        // Calculate overall score
        CalculationResult calcResult = riskScoreCalculator.calculate(ruleResults);

        // Filter to triggered issues
        List<RuleResult> triggeredRules = ruleResults.stream()
                .filter(RuleResult::isTriggered)
                .collect(Collectors.toList());

        // Generate alerts if persisting is requested
        if (persistAlerts) {
            for (RuleResult result : triggeredRules) {
                MaintenanceAlert alert = MaintenanceAlert.builder()
                        .machine(machine)
                        .alertType(result.getAlertType())
                        .severity(result.getSeverity())
                        .message(result.getMessage())
                        .resolved(false)
                        .build();
                alertRepository.save(alert);
            }
        }

        // Extract issue messages for the report
        List<String> issues = triggeredRules.stream()
                .map(RuleResult::getMessage)
                .collect(Collectors.toList());

        return HealthReport.builder()
                .machine(machine.getMachineCode())
                .healthStatus(calcResult.status())
                .riskScore(calcResult.score())
                .issues(issues)
                .build();
    }
}
