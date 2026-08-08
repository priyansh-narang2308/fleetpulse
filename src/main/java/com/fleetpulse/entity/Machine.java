package com.fleetpulse.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Represents an industrial machine/equipment in the fleet.
 *
 * <p>Each machine has a unique code (e.g., CAT-EXC-001), belongs to a location,
 * and can have many telemetry readings and maintenance alerts associated with it.</p>
 */
@Entity
@Table(name = "machines", indexes = {
        @Index(name = "idx_machine_code", columnList = "machineCode", unique = true),
        @Index(name = "idx_machine_status", columnList = "status")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String machineCode;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 50)
    private String type;

    @Column(length = 50)
    private String model;

    @Column(length = 100)
    private String location;

    @Column(length = 100)
    private String manufacturer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MachineStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
        if (this.status == null) {
            this.status = MachineStatus.ACTIVE;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
