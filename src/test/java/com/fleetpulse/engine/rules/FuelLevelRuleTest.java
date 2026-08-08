package com.fleetpulse.engine.rules;

import com.fleetpulse.engine.RuleResult;
import com.fleetpulse.entity.AlertType;
import com.fleetpulse.entity.Severity;
import com.fleetpulse.entity.Telemetry;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FuelLevelRuleTest {

    private final FuelLevelRule rule = new FuelLevelRule();

    @Test
    void evaluate_Normal() {
        Telemetry t = Telemetry.builder().fuelLevel(50.0).build();
        RuleResult result = rule.evaluate(t);
        assertFalse(result.isTriggered());
    }

    @Test
    void evaluate_LowFuel() {
        Telemetry t = Telemetry.builder().fuelLevel(15.0).build();
        RuleResult result = rule.evaluate(t);

        assertTrue(result.isTriggered());
        assertEquals(Severity.WARNING, result.getSeverity());
        assertEquals(AlertType.LOW_FUEL, result.getAlertType());
    }
}
