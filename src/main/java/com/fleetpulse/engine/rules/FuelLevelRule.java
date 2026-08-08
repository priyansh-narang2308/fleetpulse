package com.fleetpulse.engine.rules;

import com.fleetpulse.engine.HealthRule;
import com.fleetpulse.engine.RuleResult;
import com.fleetpulse.entity.AlertType;
import com.fleetpulse.entity.Severity;
import com.fleetpulse.entity.Telemetry;
import org.springframework.stereotype.Component;

@Component
public class FuelLevelRule implements HealthRule {

    private static final double LOW_FUEL_THRESHOLD = 20.0;

    @Override
    public RuleResult evaluate(Telemetry telemetry) {
        double fuelLevel = telemetry.getFuelLevel();

        if (fuelLevel < LOW_FUEL_THRESHOLD) {
            return RuleResult.builder()
                    .isTriggered(true)
                    .alertType(AlertType.LOW_FUEL)
                    .severity(Severity.WARNING)
                    .message(String.format("Low fuel warning: %.1f%% remaining", fuelLevel))
                    .riskScoreContribution(10) // Low fuel adds minor risk compared to mechanical failures
                    .build();
        }

        return RuleResult.normal();
    }
}
