package com.cibertec.client;

import com.cibertec.client.dto.CustomerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service")
public interface CustomerClient {
    @GetMapping("customers/{id}")
    CustomerResponse findById(@PathVariable Long id);
}
