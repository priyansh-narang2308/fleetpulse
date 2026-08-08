package com.fleetpulse.dto;

import com.fleetpulse.entity.MachineStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MachineResponse {

    private Long id;
    private String machineCode;
    private String name;
    private String type;
    private String model;
    private String location;
    private String manufacturer;
    private MachineStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
