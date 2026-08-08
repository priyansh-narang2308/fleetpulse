package com.fleetpulse.controller;

import com.fleetpulse.dto.HealthReport;
import com.fleetpulse.service.HealthAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/machines/{machineId}/health")
@RequiredArgsConstructor
public class HealthController {

    private final HealthAnalysisService healthAnalysisService;

    @GetMapping
    public ResponseEntity<HealthReport> getMachineHealth(@PathVariable Long machineId) {
        HealthReport report = healthAnalysisService.analyzeMachineHealth(machineId);
        return ResponseEntity.ok(report);
    }
}
