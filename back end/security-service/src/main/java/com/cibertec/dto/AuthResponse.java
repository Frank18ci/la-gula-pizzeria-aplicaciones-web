package com.cibertec.dto;

import lombok.Builder;

@Builder
public record AuthResponse(
        String token,
        String type,
        Long id,
        String email,
        String fullName
) {
}
