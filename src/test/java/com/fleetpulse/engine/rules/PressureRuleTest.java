package com.fleetpulse.engine.rules;

import com.fleetpulse.engine.RuleResult;
import com.fleetpulse.entity.AlertType;
import com.fleetpulse.entity.Severity;
import com.fleetpulse.entity.Telemetry;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PressureRuleTest {

    private final PressureRule rule = new PressureRule();

    @Test
    void evaluate_Normal() {
        Telemetry t = Telemetry.builder().pressure(110.0).build();
        RuleResult result = rule.evaluate(t);
        assertFalse(result.isTriggered());
    }

    @Test
    void evaluate_WarningLow() {
        Telemetry t = Telemetry.builder().pressure(80.0).build();
        RuleResult result = rule.evaluate(t);

        assertTrue(result.isTriggered());
        assertEquals(Severity.WARNING, result.getSeverity());
        assertEquals(AlertType.ABNORMAL_PRESSURE, result.getAlertType());
    }

    @Test
    void evaluate_WarningHigh() {
        Telemetry t = Telemetry.builder().pressure(140.0).build();
        RuleResult result = rule.evaluate(t);

        assertTrue(result.isTriggered());
        assertEquals(Severity.WARNING, result.getSeverity());
    }

    @Test
    void evaluate_CriticalLow() {
        Telemetry t = Telemetry.builder().pressure(50.0).build();
        RuleResult result = rule.evaluate(t);

        assertTrue(result.isTriggered());
        assertEquals(Severity.CRITICAL, result.getSeverity());
    }

    @Test
    void evaluate_CriticalHigh() {
        Telemetry t = Telemetry.builder().pressure(160.0).build();
        RuleResult result = rule.evaluate(t);

        assertTrue(result.isTriggered());
        assertEquals(Severity.CRITICAL, result.getSeverity());
    }
}
