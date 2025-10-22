package com.cibertec.controller;

import com.cibertec.client.dto.UserRequest;
import com.cibertec.dto.AuthRequest;
import com.cibertec.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest authRequest) {
        return ResponseEntity.ok(authService.login(authRequest));
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserRequest userRequest) {
        return ResponseEntity.ok(authService.register(userRequest));
    }
}
