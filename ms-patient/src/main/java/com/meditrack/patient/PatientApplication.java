package com.meditrack.patient;

import com.meditrack.patient.dto.PatientDTO;
import com.meditrack.patient.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class PatientApplication {
    public static void main(String[] args) {
        SpringApplication.run(PatientApplication.class, args);
    }

    @Bean
    CommandLineRunner initPatientData(PatientService patientService) {
        return args -> {
            log.info("🔧 Initializing Patient data...");

            try {
                // Patient 1
                PatientDTO patient1 = new PatientDTO();
                patient1.setFirstName("Sophie");
                patient1.setLastName("Bernard");
                patient1.setDateOfBirth(LocalDate.of(1985, 3, 15));
                patient1.setEmail("sophie.bernard@email.com");
                patient1.setPhoneNumber("+33 6 12 34 56 78");
                patient1.setRegistrationNumber("PAT001");
                patientService.createPatient(patient1);
                log.info("✅ Created Patient: Sophie Bernard (PAT001)");

                // Patient 2
                PatientDTO patient2 = new PatientDTO();
                patient2.setFirstName("Thomas");
                patient2.setLastName("Rousseau");
                patient2.setDateOfBirth(LocalDate.of(1992, 7, 22));
                patient2.setEmail("thomas.rousseau@email.com");
                patient2.setPhoneNumber("+33 6 23 45 67 89");
                patient2.setRegistrationNumber("PAT002");
                patientService.createPatient(patient2);
                log.info("✅ Created Patient: Thomas Rousseau (PAT002)");

                // Patient 3
                PatientDTO patient3 = new PatientDTO();
                patient3.setFirstName("Emma");
                patient3.setLastName("Petit");
                patient3.setDateOfBirth(LocalDate.of(2000, 11, 8));
                patient3.setEmail("emma.petit@email.com");
                patient3.setPhoneNumber("+33 6 34 56 78 90");
                patient3.setRegistrationNumber("PAT003");
                patientService.createPatient(patient3);
                log.info("✅ Created Patient: Emma Petit (PAT003)");

                log.info("🎉 Patient data initialization completed successfully!");

            } catch (Exception e) {
                log.warn("⚠️ Patient data initialization failed (might already exist): {}", e.getMessage());
            }
        };
    }
}
