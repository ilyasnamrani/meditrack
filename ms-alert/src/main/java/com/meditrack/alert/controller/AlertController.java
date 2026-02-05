package com.meditrack.alert.controller;

import com.meditrack.alert.dto.AlertDTO;
import com.meditrack.alert.service.AlertService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE')")
    public ResponseEntity<List<AlertDTO>> getAllAlerts() {
        return ResponseEntity.ok(alertService.getAllAlerts());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE')")
    public ResponseEntity<AlertDTO> getAlertById(@PathVariable Long id) {
        return ResponseEntity.ok(alertService.getAlertById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE')")
    public ResponseEntity<AlertDTO> createAlert(@Valid @RequestBody AlertDTO alertDTO) {
        return ResponseEntity.ok(alertService.createAlert(alertDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE')")
    public ResponseEntity<AlertDTO> updateAlert(@PathVariable Long id, @Valid @RequestBody AlertDTO alertDTO) {
        return ResponseEntity.ok(alertService.updateAlert(id, alertDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> deleteAlert(@PathVariable Long id) {
        alertService.deleteAlert(id);
        return ResponseEntity.noContent().build();
    }
}
