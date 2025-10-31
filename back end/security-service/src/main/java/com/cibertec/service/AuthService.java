package com.cibertec.service;

import com.cibertec.client.dto.UserRequest;
import com.cibertec.client.dto.UserResponse;
import com.cibertec.dto.AuthRequest;
import com.cibertec.dto.AuthResponse;
import jakarta.validation.Valid;

public interface AuthService {
    AuthResponse login(AuthRequest authRequest);
    UserResponse register(UserRequest userRequest);
    UserResponse registerAdmin(UserRequest userRequest);
}
