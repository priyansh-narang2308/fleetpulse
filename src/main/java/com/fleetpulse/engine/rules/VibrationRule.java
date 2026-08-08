package com.fleetpulse.engine.rules;

import com.fleetpulse.engine.HealthRule;
import com.fleetpulse.engine.RuleResult;
import com.fleetpulse.entity.AlertType;
import com.fleetpulse.entity.Severity;
import com.fleetpulse.entity.Telemetry;
import org.springframework.stereotype.Component;

@Component
public class VibrationRule implements HealthRule {

    private static final double WARNING_THRESHOLD = 5.0;
    private static final double CRITICAL_THRESHOLD = 8.0;

    @Override
    public RuleResult evaluate(Telemetry telemetry) {
        double vibration = telemetry.getVibration();

        if (vibration >= CRITICAL_THRESHOLD) {
            return RuleResult.builder()
                    .isTriggered(true)
                    .alertType(AlertType.HIGH_VIBRATION)
                    .severity(Severity.CRITICAL)
                    .message(String.format("Critical vibration detected: %.1f mm/s", vibration))
                    .riskScoreContribution(40)
                    .build();
        } else if (vibration >= WARNING_THRESHOLD) {
            return RuleResult.builder()
                    .isTriggered(true)
                    .alertType(AlertType.HIGH_VIBRATION)
                    .severity(Severity.WARNING)
                    .message(String.format("Warning high vibration: %.1f mm/s", vibration))
                    .riskScoreContribution(20)
                    .build();
        }

        return RuleResult.normal();
    }
}
