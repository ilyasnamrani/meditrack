package com.meditrack.planning.repository;

import com.meditrack.planning.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
}
