package com.cibertec.client;

import com.cibertec.client.dto.CustomerRequest;
import com.cibertec.client.dto.CustomerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "customer-service")
public interface CustomerClient {
    @PostMapping("/customers")
    CustomerResponse createCustomer(@RequestBody CustomerRequest customerRequest);
}
