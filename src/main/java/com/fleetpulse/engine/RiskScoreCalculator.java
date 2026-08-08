package com.fleetpulse.engine;

import com.fleetpulse.entity.HealthStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RiskScoreCalculator {

    /**
     * Calculates the overall health status and risk score based on rule results.
     * 0-30 = LOW (HEALTHY)
     * 31-70 = MEDIUM (WARNING)
     * 71-100 = HIGH (CRITICAL)
     */
    public CalculationResult calculate(List<RuleResult> results) {
        int totalScore = 0;

        for (RuleResult result : results) {
            if (result.isTriggered()) {
                totalScore += result.getRiskScoreContribution();
            }
        }

        // Cap score at 100
        int finalScore = Math.min(totalScore, 100);
        
        HealthStatus status;
        if (finalScore <= 30) {
            status = HealthStatus.HEALTHY;
        } else if (finalScore <= 70) {
            status = HealthStatus.WARNING;
        } else {
            status = HealthStatus.CRITICAL;
        }

        return new CalculationResult(finalScore, status);
    }
    
    public record CalculationResult(int score, HealthStatus status) {}
}
