package com.meditrack.auth.repository;

import com.meditrack.auth.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    Optional<Staff> findByEmail(String email);
    Optional<Staff> findByKeycloakId(String keycloakId);
}
