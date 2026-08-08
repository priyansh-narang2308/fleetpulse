package com.fleetpulse.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TelemetryResponse {

    private Long id;
    private Long machineId;
    private Double temperature;
    private Double fuelLevel;
    private Double vibration;
    private Double engineHours;
    private Double pressure;
    private LocalDateTime recordedAt;
}
