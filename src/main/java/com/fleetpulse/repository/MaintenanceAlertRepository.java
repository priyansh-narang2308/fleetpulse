package com.fleetpulse.repository;

import com.fleetpulse.entity.MaintenanceAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceAlertRepository extends JpaRepository<MaintenanceAlert, Long> {

    List<MaintenanceAlert> findByMachineIdOrderByCreatedAtDesc(Long machineId);

    List<MaintenanceAlert> findByResolvedFalse();

    List<MaintenanceAlert> findByMachineIdAndResolvedFalse(Long machineId);
}
