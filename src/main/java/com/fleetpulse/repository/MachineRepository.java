package com.fleetpulse.repository;

import com.fleetpulse.entity.Machine;
import com.fleetpulse.entity.MachineStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MachineRepository extends JpaRepository<Machine, Long> {

    Optional<Machine> findByMachineCode(String machineCode);

    boolean existsByMachineCode(String machineCode);

    List<Machine> findByStatus(MachineStatus status);
}
