package com.fleetpulse.engine;

import com.fleetpulse.entity.AlertType;
import com.fleetpulse.entity.Severity;
import lombok.Builder;
import lombok.Data;

/**
 * Represents the outcome of evaluating a single HealthRule.
 */
@Data
@Builder
public class RuleResult {
    private boolean isTriggered;
    private AlertType alertType;
    private Severity severity;
    private String message;
    private int riskScoreContribution;
    
    public static RuleResult normal() {
        return RuleResult.builder()
                .isTriggered(false)
                .riskScoreContribution(0)
                .build();
    }
}
