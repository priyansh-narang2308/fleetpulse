package com.fleetpulse.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TelemetryRequest {

    @NotNull(message = "Machine ID is required")
    private Long machineId;

    @NotNull(message = "Temperature is required")
    private Double temperature;

    @NotNull(message = "Fuel level is required")
    @PositiveOrZero(message = "Fuel level cannot be negative")
    private Double fuelLevel;

    @NotNull(message = "Vibration is required")
    @PositiveOrZero(message = "Vibration cannot be negative")
    private Double vibration;

    @NotNull(message = "Engine hours are required")
    @PositiveOrZero(message = "Engine hours cannot be negative")
    private Double engineHours;

    @NotNull(message = "Pressure is required")
    @PositiveOrZero(message = "Pressure cannot be negative")
    private Double pressure;
}
