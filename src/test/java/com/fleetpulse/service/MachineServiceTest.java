package com.fleetpulse.service;

import com.fleetpulse.dto.MachineRequest;
import com.fleetpulse.dto.MachineResponse;
import com.fleetpulse.entity.Machine;
import com.fleetpulse.entity.MachineStatus;
import com.fleetpulse.exception.DuplicateResourceException;
import com.fleetpulse.exception.ResourceNotFoundException;
import com.fleetpulse.repository.MachineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MachineServiceTest {

    @Mock
    private MachineRepository machineRepository;

    @InjectMocks
    private MachineService machineService;

    private Machine machine;
    private MachineRequest machineRequest;

    @BeforeEach
    void setUp() {
        machine = Machine.builder()
                .id(1L)
                .machineCode("CAT-123")
                .name("Test Machine")
                .type("Excavator")
                .status(MachineStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        machineRequest = MachineRequest.builder()
                .machineCode("CAT-123")
                .name("Test Machine")
                .type("Excavator")
                .build();
    }

    @Test
    void createMachine_Success() {
        when(machineRepository.existsByMachineCode("CAT-123")).thenReturn(false);
        when(machineRepository.save(any(Machine.class))).thenReturn(machine);

        MachineResponse response = machineService.createMachine(machineRequest);

        assertNotNull(response);
        assertEquals("CAT-123", response.getMachineCode());
        verify(machineRepository).save(any(Machine.class));
    }

    @Test
    void createMachine_DuplicateCode_ThrowsException() {
        when(machineRepository.existsByMachineCode("CAT-123")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> machineService.createMachine(machineRequest));
        verify(machineRepository, never()).save(any(Machine.class));
    }

    @Test
    void getMachineById_Success() {
        when(machineRepository.findById(1L)).thenReturn(Optional.of(machine));

        MachineResponse response = machineService.getMachineById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    @Test
    void getMachineById_NotFound_ThrowsException() {
        when(machineRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> machineService.getMachineById(1L));
    }

    @Test
    void getAllMachines_Success() {
        when(machineRepository.findAll()).thenReturn(Arrays.asList(machine));

        List<MachineResponse> responses = machineService.getAllMachines();

        assertFalse(responses.isEmpty());
        assertEquals(1, responses.size());
    }
}
