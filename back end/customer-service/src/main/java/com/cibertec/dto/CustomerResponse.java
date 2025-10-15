package com.cibertec.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CustomerResponse(
        Long id,
        Long userId,
        Long loyaltyPoints,
        LocalDateTime birthDate,
        String notes,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

}
