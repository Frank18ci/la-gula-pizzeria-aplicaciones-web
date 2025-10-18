package com.cibertec.service.impl;

import com.cibertec.client.UserClient;
import com.cibertec.client.dto.UserRequest;
import com.cibertec.client.dto.UserResponse;
import com.cibertec.dto.AuthRequest;
import com.cibertec.exception.BadRequest;
import com.cibertec.security.util.JwtUtils;
import com.cibertec.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserClient userClient;
    private final JwtUtils jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String login(AuthRequest authRequest) {
        UserResponse user = userClient.getUserByEmail(authRequest.username());

        if (user == null) {
            throw new BadRequest("User not found");
        }

        if (!passwordEncoder.matches(authRequest.password(), user.password())) {
            throw new BadRequest("Invalid email or password");
        }
        return jwtUtil.generateAccessToken(user.email());
    }

    @Override
    public UserResponse register(UserRequest userRequest) {
        userRequest = new UserRequest(
                userRequest.email(),
                passwordEncoder.encode(userRequest.password()),
                userRequest.fullName(),
                userRequest.phone(),
                userRequest.status()
        );
        return userClient.createUser(userRequest);
    }
}
