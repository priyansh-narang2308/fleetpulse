package com.fleetpulse.dto;

import com.fleetpulse.entity.HealthStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealthReport {

    private String machine;
    private HealthStatus healthStatus;
    private int riskScore;
    private List<String> issues;
}
