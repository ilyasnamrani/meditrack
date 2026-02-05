package com.meditrack.auth.controller;

import com.meditrack.auth.dto.StaffDTO;
import com.meditrack.auth.service.StaffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
public class StaffController {

    private final StaffService staffService;

    @PostMapping("/register")
    public ResponseEntity<StaffDTO> registerStaff(@Valid @RequestBody StaffDTO staffDTO) {
        return ResponseEntity.ok(staffService.createStaff(staffDTO));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<StaffDTO>> getAllStaff() {
        return ResponseEntity.ok(staffService.getAllStaff());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE', 'SECRETARY')")
    public ResponseEntity<StaffDTO> getStaffById(@PathVariable Long id) {
        return ResponseEntity.ok(staffService.getStaffById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StaffDTO> updateStaff(@PathVariable Long id, @Valid @RequestBody StaffDTO staffDTO) {
        return ResponseEntity.ok(staffService.updateStaff(id, staffDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable Long id) {
        staffService.deleteStaff(id);
        return ResponseEntity.noContent().build();
    }
}
