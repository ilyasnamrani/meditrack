package com.meditrack.alert.service;

import com.meditrack.alert.dto.AlertDTO;
import com.meditrack.alert.model.Alert;
import com.meditrack.alert.model.Alert;
import com.meditrack.alert.repository.AlertRepository;
import com.meditrack.alert.client.AuthClient;
import com.meditrack.alert.dto.StaffVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final AlertRepository alertRepository;
    private final AuthClient authClient;

    public List<AlertDTO> getAlertsForUser(Long userId) {
        return alertRepository.findByTargetUserId(userId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<AlertDTO> getAllAlerts() {
        return alertRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public AlertDTO getAlertById(Long id) {
        return alertRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Alert not found with id: " + id));
    }

    @Transactional
    public AlertDTO createAlert(AlertDTO alertDTO) {
        Alert alert = new Alert();
        alert.setTitle(alertDTO.getTitle());
        alert.setMessage(alertDTO.getMessage());
        alert.setLevel(alertDTO.getLevel());
        alert.setTargetUserId(alertDTO.getTargetUserId());

        // Enrich with Email from MS-Auth if not provided
        if ((alertDTO.getTargetEmail() == null || alertDTO.getTargetEmail().isEmpty())
                && alertDTO.getTargetUserId() != null) {
            try {
                StaffVO staff = authClient.getStaffById(alertDTO.getTargetUserId());
                alert.setTargetEmail(staff.getEmail());
            } catch (Exception e) {
                // Log warning, proceed without email
                System.err.println("Could not fetch email for user " + alertDTO.getTargetUserId());
            }
        } else {
            alert.setTargetEmail(alertDTO.getTargetEmail());
        }

        alert.setTimestamp(LocalDateTime.now());

        // Bonus: Notify Logic (Sending email/SMS) would go here

        Alert savedAlert = alertRepository.save(alert);
        return mapToDTO(savedAlert);
    }

    @Transactional
    public AlertDTO updateAlert(Long id, AlertDTO alertDTO) {
        Alert alert = alertRepository.findById(id)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Alert not found"));

        alert.setTitle(alertDTO.getTitle());
        alert.setMessage(alertDTO.getMessage());
        alert.setLevel(alertDTO.getLevel());
        // Typically we don't update targetUserId/Email or Timestamp for an alert,
        // but it depends on requirements. For now, allowing basic content updates.

        Alert updatedAlert = alertRepository.save(alert);
        return mapToDTO(updatedAlert);
    }

    public void deleteAlert(Long id) {
        if (!alertRepository.existsById(id)) {
            throw new jakarta.persistence.EntityNotFoundException("Alert not found with id: " + id);
        }
        alertRepository.deleteById(id);
    }

    private AlertDTO mapToDTO(Alert alert) {
        AlertDTO dto = new AlertDTO();
        dto.setId(alert.getId());
        dto.setTitle(alert.getTitle());
        dto.setMessage(alert.getMessage());
        dto.setLevel(alert.getLevel());
        dto.setTargetUserId(alert.getTargetUserId());
        dto.setTargetEmail(alert.getTargetEmail());
        dto.setTimestamp(alert.getTimestamp());
        dto.setRead(alert.isRead());
        return dto;
    }
}
