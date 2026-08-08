package com.fleetpulse.service;

import com.fleetpulse.dto.HealthReport;
import com.fleetpulse.engine.HealthRule;
import com.fleetpulse.engine.RiskScoreCalculator;
import com.fleetpulse.engine.RuleResult;
import com.fleetpulse.entity.*;
import com.fleetpulse.repository.MaintenanceAlertRepository;
import com.fleetpulse.repository.TelemetryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HealthAnalysisServiceTest {

    @Mock
    private TelemetryRepository telemetryRepository;

    @Mock
    private MaintenanceAlertRepository alertRepository;

    @Mock
    private MachineService machineService;

    @Mock
    private HealthRule mockRule1;

    @Mock
    private HealthRule mockRule2;

    private HealthAnalysisService healthAnalysisService;

    @BeforeEach
    void setUp() {
        RiskScoreCalculator calculator = new RiskScoreCalculator();
        healthAnalysisService = new HealthAnalysisService(
                telemetryRepository,
                alertRepository,
                Arrays.asList(mockRule1, mockRule2),
                calculator,
                machineService
        );
    }

    @Test
    void analyzeMachineHealth_AllNormal() {
        Machine machine = Machine.builder().id(1L).machineCode("M1").build();
        Telemetry telemetry = Telemetry.builder().build();

        when(machineService.getMachineEntityById(1L)).thenReturn(machine);
        when(telemetryRepository.findTopByMachineIdOrderByRecordedAtDesc(1L)).thenReturn(Optional.of(telemetry));

        when(mockRule1.evaluate(telemetry)).thenReturn(RuleResult.normal());
        when(mockRule2.evaluate(telemetry)).thenReturn(RuleResult.normal());

        HealthReport report = healthAnalysisService.analyzeMachineHealth(1L);

        assertEquals(HealthStatus.HEALTHY, report.getHealthStatus());
        assertEquals(0, report.getRiskScore());
        assertEquals(0, report.getIssues().size());
        
        // Ensure we don't save alerts when doing on-demand analysis
        verify(alertRepository, never()).save(any());
    }

    @Test
    void analyzeAndAlert_TriggersAlerts() {
        Machine machine = Machine.builder().id(1L).machineCode("M1").build();
        Telemetry telemetry = Telemetry.builder().build();

        RuleResult criticalResult = RuleResult.builder()
                .isTriggered(true)
                .alertType(AlertType.OVERHEATING)
                .severity(Severity.CRITICAL)
                .message("Overheating")
                .riskScoreContribution(80)
                .build();

        when(mockRule1.evaluate(telemetry)).thenReturn(criticalResult);
        when(mockRule2.evaluate(telemetry)).thenReturn(RuleResult.normal());

        HealthReport report = healthAnalysisService.analyzeAndAlert(machine, telemetry);

        assertEquals(HealthStatus.CRITICAL, report.getHealthStatus());
        assertEquals(80, report.getRiskScore());
        assertEquals(1, report.getIssues().size());

        // Ensure alert was persisted
        verify(alertRepository, times(1)).save(any(MaintenanceAlert.class));
    }
}
