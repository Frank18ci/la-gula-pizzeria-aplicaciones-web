package com.cibertec.service.impl;

import com.cibertec.client.CustomerClient;
import com.cibertec.client.UserClient;
import com.cibertec.client.dto.*;
import com.cibertec.dto.AuthRequest;
import com.cibertec.dto.AuthResponse;
import com.cibertec.exception.BadRequest;
import com.cibertec.producer.UserProducer;
import com.cibertec.rabbit.UserResponseRabbit;
import com.cibertec.security.util.JwtUtils;
import com.cibertec.service.AuthService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserClient userClient;
    private final CustomerClient customerClient;

    private final JwtUtils jwtUtil;
    private final PasswordEncoder passwordEncoder;

    private final UserProducer userProducer;

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
    @Transactional
    public UserResponse register(UserRequest userRequest) {
        userRequest = new UserRequest(
                userRequest.email(),
                passwordEncoder.encode(userRequest.password()),
                userRequest.fullName(),
                userRequest.phone(),
                userRequest.status()
        );
        UserResponse userResponse = userClient.createUser(userRequest);

        createCustomerWithCircuitBreaker(userResponse, "Usuario Nuevo");

        userClient.saveUserRole(UserRoleRequest.builder()
                .userId(userResponse.id())
                .roleId(2L)
                .build());
        //Create user with ROLE_USER by default

        userProducer.sendUser(UserResponseRabbit.builder()
                .email(userResponse.email())
                .fullName(userResponse.fullName())
                .phone(userResponse.phone())
                .status(userResponse.status())
                .createdAt(userResponse.createdAt())
                .roleName("USER")
                .build());

        return userResponse;
    }
    private static final String CUSTOMER_SERVICE = "customerService";
    @CircuitBreaker(name = CUSTOMER_SERVICE, fallbackMethod = "fallbackCreateCustomer")
    public void createCustomerWithCircuitBreaker(UserResponse userResponse, String notes) {
        customerClient.createCustomer(
                CustomerRequest.builder()
                        .userId(userResponse.id())
                        .notes(notes)
                        .loyaltyPoints(100)
                        .birthDate(LocalDateTime.now())
                        .build()
        );
    }

    public void fallbackCreateCustomer(UserResponse userResponse, String notes, Throwable t) {
        throw new BadRequest("Customer Service is unavailable. Please try again later.");
    }

    @Override
    @Transactional
    public UserResponse registerAdmin(UserRequest userRequest) {
        userRequest = new UserRequest(
                userRequest.email(),
                passwordEncoder.encode(userRequest.password()),
                userRequest.fullName(),
                userRequest.phone(),
                userRequest.status()
        );
        UserResponse userResponse = userClient.createUser(userRequest);

        createCustomerWithCircuitBreaker(userResponse, "Admin Nuevo");

        userClient.saveUserRole(UserRoleRequest.builder()
                .userId(userResponse.id())
                .roleId(1L)
                .build());
        //Create user with ROLE_ADMIN by default

        userProducer.sendUser(UserResponseRabbit.builder()
                .email(userResponse.email())
                .fullName(userResponse.fullName())
                .phone(userResponse.phone())
                .status(userResponse.status())
                .createdAt(userResponse.createdAt())
                .roleName("ADMIN")
                .build());

        return userResponse;
    }
}
