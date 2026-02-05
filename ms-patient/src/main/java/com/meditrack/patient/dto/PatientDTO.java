package com.meditrack.patient.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class PatientDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String email;
    private String phoneNumber;
    private String registrationNumber;
    private List<MedicalRecordDTO> medicalRecords;
}
