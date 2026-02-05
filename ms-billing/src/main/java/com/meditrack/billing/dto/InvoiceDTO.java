package com.meditrack.billing.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class InvoiceDTO {
    private Long id;
    private Long patientId;
    private Long appointmentId;
    private BigDecimal amount;
    private String status;
    private LocalDateTime issueDate;
}
