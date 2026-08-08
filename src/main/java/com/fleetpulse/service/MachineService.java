package com.fleetpulse.service;

import com.fleetpulse.dto.MachineRequest;
import com.fleetpulse.dto.MachineResponse;
import com.fleetpulse.entity.Machine;
import com.fleetpulse.exception.DuplicateResourceException;
import com.fleetpulse.exception.ResourceNotFoundException;
import com.fleetpulse.repository.MachineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MachineService {

    private final MachineRepository machineRepository;

    @Transactional
    public MachineResponse createMachine(MachineRequest request) {
        if (machineRepository.existsByMachineCode(request.getMachineCode())) {
            throw new DuplicateResourceException("Machine code already exists: " + request.getMachineCode());
        }

        Machine machine = Machine.builder()
                .machineCode(request.getMachineCode())
                .name(request.getName())
                .type(request.getType())
                .model(request.getModel())
                .location(request.getLocation())
                .manufacturer(request.getManufacturer())
                .build();

        machine = machineRepository.save(machine);
        return mapToResponse(machine);
    }

    @Transactional(readOnly = true)
    public List<MachineResponse> getAllMachines() {
        return machineRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MachineResponse getMachineById(Long id) {
        Machine machine = getMachineEntityById(id);
        return mapToResponse(machine);
    }

    @Transactional
    public MachineResponse updateMachine(Long id, MachineRequest request) {
        Machine machine = getMachineEntityById(id);

        if (!machine.getMachineCode().equals(request.getMachineCode()) && 
            machineRepository.existsByMachineCode(request.getMachineCode())) {
            throw new DuplicateResourceException("Machine code already exists: " + request.getMachineCode());
        }

        machine.setMachineCode(request.getMachineCode());
        machine.setName(request.getName());
        machine.setType(request.getType());
        machine.setModel(request.getModel());
        machine.setLocation(request.getLocation());
        machine.setManufacturer(request.getManufacturer());

        machine = machineRepository.save(machine);
        return mapToResponse(machine);
    }

    @Transactional
    public void deleteMachine(Long id) {
        if (!machineRepository.existsById(id)) {
            throw new ResourceNotFoundException("Machine not found with id: " + id);
        }
        machineRepository.deleteById(id);
    }

    public Machine getMachineEntityById(Long id) {
        return machineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Machine not found with id: " + id));
    }

    private MachineResponse mapToResponse(Machine machine) {
        return MachineResponse.builder()
                .id(machine.getId())
                .machineCode(machine.getMachineCode())
                .name(machine.getName())
                .type(machine.getType())
                .model(machine.getModel())
                .location(machine.getLocation())
                .manufacturer(machine.getManufacturer())
                .status(machine.getStatus())
                .createdAt(machine.getCreatedAt())
                .updatedAt(machine.getUpdatedAt())
                .build();
    }
}
