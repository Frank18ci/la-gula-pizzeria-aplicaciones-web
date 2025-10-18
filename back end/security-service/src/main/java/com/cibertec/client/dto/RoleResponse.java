package com.cibertec.client.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record RoleResponse(
        Long id,
        String name,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

}
