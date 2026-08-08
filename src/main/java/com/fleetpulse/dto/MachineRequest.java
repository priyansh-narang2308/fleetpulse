package com.fleetpulse.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MachineRequest {

    @NotBlank(message = "Machine code is required")
    @Size(max = 50, message = "Machine code cannot exceed 50 characters")
    private String machineCode;

    @NotBlank(message = "Machine name is required")
    @Size(max = 100, message = "Machine name cannot exceed 100 characters")
    private String name;

    @NotBlank(message = "Machine type is required")
    @Size(max = 50, message = "Machine type cannot exceed 50 characters")
    private String type;

    @Size(max = 50, message = "Machine model cannot exceed 50 characters")
    private String model;

    @Size(max = 100, message = "Location cannot exceed 100 characters")
    private String location;

    @Size(max = 100, message = "Manufacturer cannot exceed 100 characters")
    private String manufacturer;
}
