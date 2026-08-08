package com.fleetpulse.service;

import com.fleetpulse.dto.AlertResponse;
import com.fleetpulse.entity.MaintenanceAlert;
import com.fleetpulse.exception.ResourceNotFoundException;
import com.fleetpulse.repository.MaintenanceAlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final MaintenanceAlertRepository alertRepository;
    private final MachineService machineService;

    @Transactional(readOnly = true)
    public List<AlertResponse> getAlertsForMachine(Long machineId) {
        // Ensure machine exists
        machineService.getMachineEntityById(machineId);

        return alertRepository.findByMachineIdOrderByCreatedAtDesc(machineId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AlertResponse> getUnresolvedAlerts() {
        return alertRepository.findByResolvedFalse().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public AlertResponse resolveAlert(Long alertId) {
        MaintenanceAlert alert = alertRepository.findById(alertId)
                .orElseThrow(() -> new ResourceNotFoundException("Alert not found with id: " + alertId));
        
        alert.setResolved(true);
        alert = alertRepository.save(alert);
        return mapToResponse(alert);
    }

    private AlertResponse mapToResponse(MaintenanceAlert alert) {
        return AlertResponse.builder()
                .id(alert.getId())
                .machineId(alert.getMachine().getId())
                .alertType(alert.getAlertType())
                .severity(alert.getSeverity())
                .message(alert.getMessage())
                .resolved(alert.getResolved())
                .createdAt(alert.getCreatedAt())
                .build();
    }
}
