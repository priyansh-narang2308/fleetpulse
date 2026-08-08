package com.fleetpulse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * FleetPulse - Industrial Equipment Monitoring and Predictive Maintenance Platform.
 *
 * <p>Monitors industrial equipment by receiving machine telemetry data,
 * analyzing equipment health through a rule-based engine, and generating
 * predictive maintenance alerts.</p>
 */
@SpringBootApplication
public class FleetPulseApplication {

    public static void main(String[] args) {
        SpringApplication.run(FleetPulseApplication.class, args);
    }
}
