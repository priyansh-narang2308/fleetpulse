package com.fleetpulse.service;

import com.fleetpulse.dto.TelemetryRequest;
import com.fleetpulse.dto.TelemetryResponse;
import com.fleetpulse.entity.Machine;
import com.fleetpulse.entity.Telemetry;
import com.fleetpulse.repository.TelemetryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TelemetryServiceTest {

    @Mock
    private TelemetryRepository telemetryRepository;

    @Mock
    private MachineService machineService;

    @Mock
    private HealthAnalysisService healthAnalysisService;

    @InjectMocks
    private TelemetryService telemetryService;

    @Test
    void ingestTelemetry_Success() {
        Machine machine = Machine.builder().id(1L).machineCode("M1").build();
        
        TelemetryRequest request = TelemetryRequest.builder()
                .machineId(1L)
                .temperature(85.0)
                .fuelLevel(50.0)
                .vibration(3.0)
                .engineHours(100.0)
                .pressure(110.0)
                .build();

        Telemetry savedTelemetry = Telemetry.builder()
                .id(1L)
                .machine(machine)
                .temperature(85.0)
                .fuelLevel(50.0)
                .vibration(3.0)
                .engineHours(100.0)
                .pressure(110.0)
                .recordedAt(LocalDateTime.now())
                .build();

        when(machineService.getMachineEntityById(1L)).thenReturn(machine);
        when(telemetryRepository.save(any(Telemetry.class))).thenReturn(savedTelemetry);

        TelemetryResponse response = telemetryService.ingestTelemetry(request);

        assertNotNull(response);
        assertEquals(85.0, response.getTemperature());
        verify(healthAnalysisService).analyzeAndAlert(machine, savedTelemetry);
    }
}
