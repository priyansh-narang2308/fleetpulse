package com.fleetpulse.controller;

import com.fleetpulse.dto.MachineRequest;
import com.fleetpulse.dto.MachineResponse;
import com.fleetpulse.service.MachineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/machines")
@RequiredArgsConstructor
public class MachineController {

    private final MachineService machineService;

    @PostMapping
    public ResponseEntity<MachineResponse> createMachine(@Valid @RequestBody MachineRequest request) {
        MachineResponse response = machineService.createMachine(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MachineResponse>> getAllMachines() {
        List<MachineResponse> responses = machineService.getAllMachines();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MachineResponse> getMachineById(@PathVariable Long id) {
        MachineResponse response = machineService.getMachineById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MachineResponse> updateMachine(
            @PathVariable Long id, 
            @Valid @RequestBody MachineRequest request) {
        MachineResponse response = machineService.updateMachine(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMachine(@PathVariable Long id) {
        machineService.deleteMachine(id);
        return ResponseEntity.noContent().build();
    }
}
