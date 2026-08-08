package com.fleetpulse.engine.rules;

import com.fleetpulse.engine.HealthRule;
import com.fleetpulse.engine.RuleResult;
import com.fleetpulse.entity.AlertType;
import com.fleetpulse.entity.Severity;
import com.fleetpulse.entity.Telemetry;
import org.springframework.stereotype.Component;

@Component
public class PressureRule implements HealthRule {

    private static final double MIN_NORMAL_PRESSURE = 90.0;
    private static final double MAX_NORMAL_PRESSURE = 130.0;

    private static final double MIN_CRITICAL_PRESSURE = 70.0;
    private static final double MAX_CRITICAL_PRESSURE = 150.0;

    @Override
    public RuleResult evaluate(Telemetry telemetry) {
        double pressure = telemetry.getPressure();

        if (pressure <= MIN_CRITICAL_PRESSURE || pressure >= MAX_CRITICAL_PRESSURE) {
            return RuleResult.builder()
                    .isTriggered(true)
                    .alertType(AlertType.ABNORMAL_PRESSURE)
                    .severity(Severity.CRITICAL)
                    .message(String.format("Critical abnormal pressure: %.1f PSI", pressure))
                    .riskScoreContribution(40)
                    .build();
        } else if (pressure < MIN_NORMAL_PRESSURE || pressure > MAX_NORMAL_PRESSURE) {
            return RuleResult.builder()
                    .isTriggered(true)
                    .alertType(AlertType.ABNORMAL_PRESSURE)
                    .severity(Severity.WARNING)
                    .message(String.format("Warning abnormal pressure: %.1f PSI", pressure))
                    .riskScoreContribution(20)
                    .build();
        }

        return RuleResult.normal();
    }
}
