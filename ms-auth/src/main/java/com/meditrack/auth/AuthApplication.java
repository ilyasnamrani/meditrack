package com.meditrack.auth;

import com.meditrack.auth.dto.StaffDTO;
import com.meditrack.auth.service.StaffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

    @Bean
    CommandLineRunner initStaffData(StaffService staffService) {
        return args -> {
            log.info("🔧 Initializing Staff data...");

            try {
                // Staff 1: Administrator
                StaffDTO admin = new StaffDTO();
                admin.setFirstName("Alice");
                admin.setLastName("Martin");
                admin.setEmail("alice.martin@meditrack.com");
                admin.setRole("ADMIN");
                admin.setPassword("Admin@123");
                staffService.createStaff(admin);
                log.info("✅ Created Admin: Alice Martin");

                // Staff 2: Doctor
                StaffDTO doctor = new StaffDTO();
                doctor.setFirstName("Dr. Jean");
                doctor.setLastName("Dupont");
                doctor.setEmail("jean.dupont@meditrack.com");
                doctor.setRole("DOCTOR");
                doctor.setPassword("Doctor@123");
                staffService.createStaff(doctor);
                log.info("✅ Created Doctor: Dr. Jean Dupont");

                // Staff 3: Nurse
                StaffDTO nurse = new StaffDTO();
                nurse.setFirstName("Marie");
                nurse.setLastName("Leblanc");
                nurse.setEmail("marie.leblanc@meditrack.com");
                nurse.setRole("NURSE");
                nurse.setPassword("Nurse@123");
                staffService.createStaff(nurse);
                log.info("✅ Created Nurse: Marie Leblanc");

                log.info("🎉 Staff data initialization completed successfully!");

            } catch (Exception e) {
                log.warn("⚠️ Staff data initialization failed (might already exist): {}", e.getMessage());
            }
        };
    }
}
