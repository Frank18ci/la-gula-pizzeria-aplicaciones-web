package com.cibertec.client;

import com.cibertec.client.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "users-service")
public interface UserClient {
    @GetMapping("users/{id}")
    UserResponse findById(@PathVariable Long id);
}
