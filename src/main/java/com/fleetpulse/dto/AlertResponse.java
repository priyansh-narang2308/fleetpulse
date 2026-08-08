package com.fleetpulse.dto;

import com.fleetpulse.entity.AlertType;
import com.fleetpulse.entity.Severity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlertResponse {

    private Long id;
    private Long machineId;
    private AlertType alertType;
    private Severity severity;
    private String message;
    private Boolean resolved;
    private LocalDateTime createdAt;
}
