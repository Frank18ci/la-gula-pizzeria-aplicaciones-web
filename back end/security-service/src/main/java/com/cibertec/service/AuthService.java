package com.cibertec.service;

import com.cibertec.client.dto.UserRequest;
import com.cibertec.client.dto.UserResponse;
import com.cibertec.dto.AuthRequest;

public interface AuthService {
    String login(AuthRequest authRequest);
    UserResponse register(UserRequest userRequest);
}
