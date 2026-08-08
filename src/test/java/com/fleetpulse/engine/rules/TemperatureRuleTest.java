package com.fleetpulse.engine.rules;

import com.fleetpulse.engine.RuleResult;
import com.fleetpulse.entity.AlertType;
import com.fleetpulse.entity.Severity;
import com.fleetpulse.entity.Telemetry;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TemperatureRuleTest {

    private final TemperatureRule rule = new TemperatureRule();

    @Test
    void evaluate_Normal() {
        Telemetry t = Telemetry.builder().temperature(75.0).build();
        RuleResult result = rule.evaluate(t);
        assertFalse(result.isTriggered());
    }

    @Test
    void evaluate_Warning() {
        Telemetry t = Telemetry.builder().temperature(85.0).build();
        RuleResult result = rule.evaluate(t);
        
        assertTrue(result.isTriggered());
        assertEquals(Severity.WARNING, result.getSeverity());
        assertEquals(AlertType.OVERHEATING, result.getAlertType());
    }

    @Test
    void evaluate_Critical() {
        Telemetry t = Telemetry.builder().temperature(95.0).build();
        RuleResult result = rule.evaluate(t);
        
        assertTrue(result.isTriggered());
        assertEquals(Severity.CRITICAL, result.getSeverity());
    }
}
