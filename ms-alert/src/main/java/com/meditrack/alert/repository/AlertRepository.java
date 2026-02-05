package com.meditrack.alert.repository;

import com.meditrack.alert.model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findByTargetUserId(Long userId);
    List<Alert> findByReadFalse();
}
