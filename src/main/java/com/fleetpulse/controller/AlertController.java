package com.fleetpulse.controller;

import com.fleetpulse.dto.AlertResponse;
import com.fleetpulse.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;

    @GetMapping("/machine/{machineId}")
    public ResponseEntity<List<AlertResponse>> getAlertsForMachine(@PathVariable Long machineId) {
        List<AlertResponse> alerts = alertService.getAlertsForMachine(machineId);
        return ResponseEntity.ok(alerts);
    }

    @GetMapping("/unresolved")
    public ResponseEntity<List<AlertResponse>> getUnresolvedAlerts() {
        List<AlertResponse> alerts = alertService.getUnresolvedAlerts();
        return ResponseEntity.ok(alerts);
    }

    @PatchMapping("/{alertId}/resolve")
    public ResponseEntity<AlertResponse> resolveAlert(@PathVariable Long alertId) {
        AlertResponse alert = alertService.resolveAlert(alertId);
        return ResponseEntity.ok(alert);
    }
}
