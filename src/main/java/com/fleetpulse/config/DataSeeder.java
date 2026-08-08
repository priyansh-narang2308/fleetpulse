package com.fleetpulse.config;

import com.fleetpulse.entity.Machine;
import com.fleetpulse.entity.MachineStatus;
import com.fleetpulse.entity.Telemetry;
import com.fleetpulse.repository.MachineRepository;
import com.fleetpulse.repository.TelemetryRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataSeeder.class);

    private final MachineRepository machineRepository;
    private final TelemetryRepository telemetryRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (machineRepository.count() == 0) {
            logger.info("No machines found. Seeding initial data...");
            seedData();
            logger.info("Database seeding completed successfully.");
        } else {
            logger.info("Database already contains data. Skipping seeder.");
        }
    }

    private void seedData() {
        // Seed Machines
        List<Machine> machines = Arrays.asList(
                createMachine("CAT-EXC-001", "Excavator Alpha", "Excavator", "EX200", "Chennai Plant", "Caterpillar"),
                createMachine("CAT-DUMP-002", "Dump Truck Bravo", "Dump Truck", "DT500", "Mumbai Site", "Caterpillar"),
                createMachine("CAT-DRILL-003", "Drill Charlie", "Drilling Rig", "DR100", "Delhi Hub", "Caterpillar"),
                createMachine("KMT-LDR-004", "Loader Delta", "Loader", "L350", "Bangalore Zone", "Komatsu"),
                createMachine("JD-TRC-005", "Tractor Echo", "Tractor", "JT900", "Pune Field", "John Deere")
        );

        machineRepository.saveAll(machines);

        // Seed Telemetry for each machine
        Random random = new Random();
        LocalDateTime now = LocalDateTime.now();

        for (Machine machine : machines) {
            // Create 10 telemetry records per machine (50 total)
            for (int i = 9; i >= 0; i--) {
                double tempBase = random.nextDouble() * 20 + 70; // 70-90
                double fuelBase = random.nextDouble() * 60 + 20; // 20-80
                double vibBase = random.nextDouble() * 4 + 2;    // 2-6
                double hours = 5000 + ((9 - i) * 10);
                double pressBase = random.nextDouble() * 30 + 100; // 100-130

                // Add a chance for anomalous data on the latest reading of the first machine
                if (i == 0 && machine.getMachineCode().equals("CAT-EXC-001")) {
                    tempBase = 95.0; // Critical temp
                    vibBase = 9.0;   // Critical vib
                }

                Telemetry t = Telemetry.builder()
                        .machine(machine)
                        .temperature(tempBase)
                        .fuelLevel(fuelBase)
                        .vibration(vibBase)
                        .engineHours(hours)
                        .pressure(pressBase)
                        .recordedAt(now.minusHours(i))
                        .build();

                telemetryRepository.save(t);
            }
        }
    }

    private Machine createMachine(String code, String name, String type, String model, String location, String manufacturer) {
        return Machine.builder()
                .machineCode(code)
                .name(name)
                .type(type)
                .model(model)
                .location(location)
                .manufacturer(manufacturer)
                .status(MachineStatus.ACTIVE)
                .build();
    }
}
