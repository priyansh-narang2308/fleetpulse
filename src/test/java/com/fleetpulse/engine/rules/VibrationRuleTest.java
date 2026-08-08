package com.fleetpulse.engine.rules;

import com.fleetpulse.engine.RuleResult;
import com.fleetpulse.entity.AlertType;
import com.fleetpulse.entity.Severity;
import com.fleetpulse.entity.Telemetry;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VibrationRuleTest {

    private final VibrationRule rule = new VibrationRule();

    @Test
    void evaluate_Normal() {
        Telemetry t = Telemetry.builder().vibration(2.0).build();
        RuleResult result = rule.evaluate(t);
        assertFalse(result.isTriggered());
    }

    @Test
    void evaluate_Warning() {
        Telemetry t = Telemetry.builder().vibration(6.0).build();
        RuleResult result = rule.evaluate(t);

        assertTrue(result.isTriggered());
        assertEquals(Severity.WARNING, result.getSeverity());
        assertEquals(AlertType.HIGH_VIBRATION, result.getAlertType());
    }

    @Test
    void evaluate_Critical() {
        Telemetry t = Telemetry.builder().vibration(9.5).build();
        RuleResult result = rule.evaluate(t);

        assertTrue(result.isTriggered());
        assertEquals(Severity.CRITICAL, result.getSeverity());
    }
}
