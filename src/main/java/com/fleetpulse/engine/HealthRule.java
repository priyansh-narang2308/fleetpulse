package com.fleetpulse.engine;

import com.fleetpulse.entity.Telemetry;

/**
 * Interface for all health analysis rules.
 * Implements the Strategy Pattern to allow easily adding new rules.
 */
public interface HealthRule {
    
    /**
     * Evaluates a telemetry reading against the specific rule threshold.
     *
     * @param telemetry The telemetry reading to evaluate.
     * @return A RuleResult containing the outcome of the evaluation.
     */
    RuleResult evaluate(Telemetry telemetry);
}
