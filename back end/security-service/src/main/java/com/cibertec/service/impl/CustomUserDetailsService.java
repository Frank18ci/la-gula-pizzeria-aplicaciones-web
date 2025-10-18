package com.cibertec.service;

import com.cibertec.client.UserClient;
import com.cibertec.client.dto.UserResponse;
import com.cibertec.client.dto.UserRoleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserClient userClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserResponse userResponse = userClient.getUserByEmail(username);
        if(userResponse == null){
            throw new UsernameNotFoundException("User not found with email: " + username);
        }
        List<UserRoleResponse> userRoleResponse = userClient.getUserRolesByUserId(userResponse.id());

        List<GrantedAuthority> authorities = userRoleResponse == null ? List.of() :
                userRoleResponse.stream()
                        .map(UserRoleResponse::role)
                        .filter(Objects::nonNull)
                        .map(Object::toString)
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .map(s -> s.toUpperCase().startsWith("ROLE_") ? s.toUpperCase() : "ROLE_" + s.toUpperCase())
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        return User.builder()
                .username(userResponse.email())
                .password(userResponse.password())
                .authorities(authorities)
                .build();
    }
}
