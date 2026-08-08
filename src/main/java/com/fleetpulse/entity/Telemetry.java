package com.fleetpulse.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Represents a single telemetry reading from a machine's sensors.
 *
 * <p>Captures temperature, fuel level, vibration, engine hours, and pressure
 * at a specific point in time. Each reading is linked to exactly one machine.</p>
 */
@Entity
@Table(name = "telemetry", indexes = {
        @Index(name = "idx_telemetry_machine_time", columnList = "machine_id, recordedAt")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Telemetry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id", nullable = false)
    private Machine machine;

    /**
     * Engine/component temperature in degrees Celsius.
     */
    @Column(nullable = false)
    private Double temperature;

    /**
     * Fuel level as a percentage (0-100).
     */
    @Column(nullable = false)
    private Double fuelLevel;

    /**
     * Vibration intensity in mm/s RMS.
     */
    @Column(nullable = false)
    private Double vibration;

    /**
     * Cumulative engine operating hours.
     */
    @Column(nullable = false)
    private Double engineHours;

    /**
     * Hydraulic/pneumatic pressure in PSI.
     */
    @Column(nullable = false)
    private Double pressure;

    /**
     * Timestamp when this sensor reading was captured.
     */
    @Column(nullable = false)
    private LocalDateTime recordedAt;

    @PrePersist
    protected void onCreate() {
        if (this.recordedAt == null) {
            this.recordedAt = LocalDateTime.now();
        }
    }
}
