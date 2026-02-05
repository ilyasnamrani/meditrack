package com.meditrack.billing.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long patientId;
    private Long appointmentId;
    
    private BigDecimal amount;
    private String status; // PENDING, PAID, CANCELLED
    
    private LocalDateTime issueDate;
}
