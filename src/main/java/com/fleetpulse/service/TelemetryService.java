package com.fleetpulse.service;

import com.fleetpulse.dto.TelemetryRequest;
import com.fleetpulse.dto.TelemetryResponse;
import com.fleetpulse.entity.Machine;
import com.fleetpulse.entity.Telemetry;
import com.fleetpulse.repository.TelemetryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TelemetryService {

    private final TelemetryRepository telemetryRepository;
    private final MachineService machineService;
    private final HealthAnalysisService healthAnalysisService;

    @Transactional
    public TelemetryResponse ingestTelemetry(TelemetryRequest request) {
        Machine machine = machineService.getMachineEntityById(request.getMachineId());

        Telemetry telemetry = Telemetry.builder()
                .machine(machine)
                .temperature(request.getTemperature())
                .fuelLevel(request.getFuelLevel())
                .vibration(request.getVibration())
                .engineHours(request.getEngineHours())
                .pressure(request.getPressure())
                .build();

        telemetry = telemetryRepository.save(telemetry);

        // Analyze and potentially trigger alerts based on this new reading
        healthAnalysisService.analyzeAndAlert(machine, telemetry);

        return mapToResponse(telemetry);
    }

    @Transactional(readOnly = true)
    public List<TelemetryResponse> getTelemetryHistory(Long machineId, int page, int size) {
        // Ensure machine exists
        machineService.getMachineEntityById(machineId);
        
        Page<Telemetry> telemetryPage = telemetryRepository.findByMachineIdOrderByRecordedAtDesc(
                machineId, PageRequest.of(page, size));
                
        return telemetryPage.getContent().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private TelemetryResponse mapToResponse(Telemetry telemetry) {
        return TelemetryResponse.builder()
                .id(telemetry.getId())
                .machineId(telemetry.getMachine().getId())
                .temperature(telemetry.getTemperature())
                .fuelLevel(telemetry.getFuelLevel())
                .vibration(telemetry.getVibration())
                .engineHours(telemetry.getEngineHours())
                .pressure(telemetry.getPressure())
                .recordedAt(telemetry.getRecordedAt())
                .build();
    }
}
