package com.cibertec.client;

import com.cibertec.client.dto.UserRequest;
import com.cibertec.client.dto.UserResponse;
import com.cibertec.client.dto.UserRoleRequest;
import com.cibertec.client.dto.UserRoleResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "users-service")
public interface UserClient {
    @GetMapping("/users/email/{email}")
    UserResponse getUserByEmail(@PathVariable String email);

    @GetMapping("/user-roles/user/{idUser}")
    List<UserRoleResponse> getUserRolesByUserId(@PathVariable Long idUser);

    @GetMapping("/user-roles")
    UserRoleResponse saveUserRole(@RequestBody UserRoleRequest userRoleRequest);

    @PostMapping("/users")
    UserResponse createUser(@RequestBody UserRequest userRequest);
}
