package com.meditrack.patient.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MedicalRecordDTO {
    private Long id;
    private LocalDateTime visitDate;
    private String diagnosis;
    private String treatment;
    private String prescription;
}
