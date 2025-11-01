package com.cibertec.client;

import com.cibertec.client.dto.OrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ordening-service")
public interface OrdeningClient {
    @GetMapping("/orders/{id}")
    OrderResponse findById(@PathVariable Long id);
}
