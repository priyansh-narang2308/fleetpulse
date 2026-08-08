package com.fleetpulse.engine.rules;

import com.fleetpulse.engine.HealthRule;
import com.fleetpulse.engine.RuleResult;
import com.fleetpulse.entity.AlertType;
import com.fleetpulse.entity.Severity;
import com.fleetpulse.entity.Telemetry;
import org.springframework.stereotype.Component;

@Component
public class TemperatureRule implements HealthRule {

    private static final double WARNING_THRESHOLD = 80.0;
    private static final double CRITICAL_THRESHOLD = 90.0;

    @Override
    public RuleResult evaluate(Telemetry telemetry) {
        double temp = telemetry.getTemperature();

        if (temp >= CRITICAL_THRESHOLD) {
            return RuleResult.builder()
                    .isTriggered(true)
                    .alertType(AlertType.OVERHEATING)
                    .severity(Severity.CRITICAL)
                    .message(String.format("Critical temperature detected: %.1f°C", temp))
                    .riskScoreContribution(40)
                    .build();
        } else if (temp >= WARNING_THRESHOLD) {
            return RuleResult.builder()
                    .isTriggered(true)
                    .alertType(AlertType.OVERHEATING)
                    .severity(Severity.WARNING)
                    .message(String.format("Warning high temperature: %.1f°C", temp))
                    .riskScoreContribution(20)
                    .build();
        }
        
        return RuleResult.normal();
    }
}
