package com.fleetpulse.controller;

import com.fleetpulse.dto.TelemetryRequest;
import com.fleetpulse.dto.TelemetryResponse;
import com.fleetpulse.service.TelemetryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/telemetry")
@RequiredArgsConstructor
public class TelemetryController {

    private final TelemetryService telemetryService;

    @PostMapping
    public ResponseEntity<TelemetryResponse> ingestTelemetry(@Valid @RequestBody TelemetryRequest request) {
        TelemetryResponse response = telemetryService.ingestTelemetry(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{machineId}")
    public ResponseEntity<List<TelemetryResponse>> getTelemetryHistory(
            @PathVariable Long machineId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        
        List<TelemetryResponse> responses = telemetryService.getTelemetryHistory(machineId, page, size);
        return ResponseEntity.ok(responses);
    }
}
