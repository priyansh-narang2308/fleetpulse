package com.fleetpulse.repository;

import com.fleetpulse.entity.Telemetry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TelemetryRepository extends JpaRepository<Telemetry, Long> {

    Page<Telemetry> findByMachineIdOrderByRecordedAtDesc(Long machineId, Pageable pageable);

    Optional<Telemetry> findTopByMachineIdOrderByRecordedAtDesc(Long machineId);

    List<Telemetry> findByMachineIdAndRecordedAtBetween(Long machineId, LocalDateTime start, LocalDateTime end);
}
