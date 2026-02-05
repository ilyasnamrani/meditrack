package com.meditrack.planning;

import com.meditrack.planning.dto.AppointmentDTO;
import com.meditrack.planning.model.Resource;
import com.meditrack.planning.model.ResourceType;
import com.meditrack.planning.repository.ResourceRepository;
import com.meditrack.planning.service.PlanningService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@Slf4j
public class PlanningApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlanningApplication.class, args);
    }

    @Bean
    CommandLineRunner initPlanningData(PlanningService planningService, ResourceRepository resourceRepository) {
        return args -> {
            log.info("🔧 Initializing Planning data...");

            try {
                // Create Resources first
                Resource room1 = new Resource();
                room1.setName("Salle de Consultation 1");
                room1.setType(ResourceType.ROOM);
                room1.setAvailable(true);
                Resource savedRoom1 = resourceRepository.save(room1);
                log.info("✅ Created Resource: Salle de Consultation 1");

                Resource room2 = new Resource();
                room2.setName("Salle de Consultation 2");
                room2.setType(ResourceType.ROOM);
                room2.setAvailable(true);
                Resource savedRoom2 = resourceRepository.save(room2);
                log.info("✅ Created Resource: Salle de Consultation 2");

                Resource room3 = new Resource();
                room3.setName("Salle d'Examen");
                room3.setType(ResourceType.ROOM);
                room3.setAvailable(true);
                Resource savedRoom3 = resourceRepository.save(room3);
                log.info("✅ Created Resource: Salle d'Examen");

                // Wait a moment to ensure ms-auth and ms-patient are ready
                Thread.sleep(2000);

                // Create Appointments (these will fail if ms-auth/ms-patient are not running)
                // Appointment 1: Tomorrow at 9:00 AM
                AppointmentDTO appointment1 = new AppointmentDTO();
                appointment1.setDoctorId(2L); // Dr. Jean Dupont from ms-auth
                appointment1.setPatientId(1L); // Sophie Bernard from ms-patient
                appointment1.setStartTime(LocalDateTime.now().plusDays(1).withHour(9).withMinute(0).withSecond(0));
                appointment1.setEndTime(LocalDateTime.now().plusDays(1).withHour(10).withMinute(0).withSecond(0));
                appointment1.setResourceId(savedRoom1.getId());
                planningService.scheduleAppointment(appointment1);
                log.info("✅ Created Appointment: Dr. Dupont with Sophie Bernard - Tomorrow 9:00");

                // Appointment 2: Tomorrow at 14:00
                AppointmentDTO appointment2 = new AppointmentDTO();
                appointment2.setDoctorId(2L); // Dr. Jean Dupont
                appointment2.setPatientId(2L); // Thomas Rousseau from ms-patient
                appointment2.setStartTime(LocalDateTime.now().plusDays(1).withHour(14).withMinute(0).withSecond(0));
                appointment2.setEndTime(LocalDateTime.now().plusDays(1).withHour(15).withMinute(0).withSecond(0));
                appointment2.setResourceId(savedRoom2.getId());
                planningService.scheduleAppointment(appointment2);
                log.info("✅ Created Appointment: Dr. Dupont with Thomas Rousseau - Tomorrow 14:00");

                // Appointment 3: In 2 days at 10:00
                AppointmentDTO appointment3 = new AppointmentDTO();
                appointment3.setDoctorId(2L); // Dr. Jean Dupont
                appointment3.setPatientId(3L); // Emma Petit from ms-patient
                appointment3.setStartTime(LocalDateTime.now().plusDays(2).withHour(10).withMinute(0).withSecond(0));
                appointment3.setEndTime(LocalDateTime.now().plusDays(2).withHour(11).withMinute(0).withSecond(0));
                appointment3.setResourceId(savedRoom3.getId());
                planningService.scheduleAppointment(appointment3);
                log.info("✅ Created Appointment: Dr. Dupont with Emma Petit - In 2 days 10:00");

                log.info("🎉 Planning data initialization completed successfully!");

            } catch (Exception e) {
                log.warn("⚠️ Planning data initialization failed: {}", e.getMessage());
                log.warn("💡 Make sure ms-auth and ms-patient are running before ms-planning!");
            }
        };
    }
}
