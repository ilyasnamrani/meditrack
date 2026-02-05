package com.meditrack.alert.dto;

import com.meditrack.alert.model.AlertLevel;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AlertDTO {
    private Long id;
    private String title;
    private String message;
    private AlertLevel level;
    private Long targetUserId;
    private String targetEmail;
    private LocalDateTime timestamp;
    private boolean read;
}
