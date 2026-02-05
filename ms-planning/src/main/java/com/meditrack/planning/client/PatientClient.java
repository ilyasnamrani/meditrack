package com.meditrack.planning.client;

import com.meditrack.planning.dto.PatientVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-patient")
public interface PatientClient {

    @GetMapping("/api/patients/{id}")
    PatientVO getPatientById(@PathVariable("id") Long id);
}
