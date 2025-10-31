package com.cibertec.service.impl;

import com.cibertec.client.UserClient;
import com.cibertec.client.dto.UserRequest;
import com.cibertec.client.dto.UserResponse;
import com.cibertec.client.dto.UserRoleRequest;
import com.cibertec.client.dto.UserRoleResponse;
import com.cibertec.dto.AuthRequest;
import com.cibertec.dto.AuthResponse;
import com.cibertec.exception.BadRequest;
import com.cibertec.security.util.JwtUtils;
import com.cibertec.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserClient userClient;
    private final JwtUtils jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        UserResponse user = userClient.getUserByEmail(authRequest.username());

        if (user == null) {
            throw new BadRequest("User not found");
        }

        if (!passwordEncoder.matches(authRequest.password(), user.password())) {
            throw new BadRequest("Invalid email or password");
        }
        String token = jwtUtil.generateAccessToken(user.email());
        List<UserRoleResponse> userRoleResponses = userClient.getUserRolesByUserId(user.id());
        return AuthResponse.builder()
                .id(user.id())
                .token(token)
                .fullName(user.fullName())
                .username(user.email())
                .roles(userRoleResponses.stream().map(ur -> ur.role().name()).toList())
                .build();
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
        UserResponse userResponse = userClient.createUser(userRequest);
        userClient.saveUserRole(UserRoleRequest.builder()
                .userId(userResponse.id())
                .roleId(2L)
                .build());
        //Create user with ROLE_USER by default
        return userResponse;
    }

    @Override
    public UserResponse registerAdmin(UserRequest userRequest) {
        userRequest = new UserRequest(
                userRequest.email(),
                passwordEncoder.encode(userRequest.password()),
                userRequest.fullName(),
                userRequest.phone(),
                userRequest.status()
        );
        UserResponse userResponse = userClient.createUser(userRequest);
        userClient.saveUserRole(UserRoleRequest.builder()
                .userId(userResponse.id())
                .roleId(1L)
                .build());
        //Create user with ROLE_ADMIN by default
        return userResponse;
    }
}
