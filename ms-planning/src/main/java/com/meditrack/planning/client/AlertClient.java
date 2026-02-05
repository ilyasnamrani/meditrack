package com.meditrack.planning.client;

import com.meditrack.planning.dto.AlertVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ms-alert")
public interface AlertClient {

    @PostMapping("/api/alerts")
    void createAlert(@RequestBody AlertVO alertVO);
}
