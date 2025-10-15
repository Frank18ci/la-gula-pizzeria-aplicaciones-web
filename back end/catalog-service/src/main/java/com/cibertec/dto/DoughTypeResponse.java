package com.cibertec.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record DoughTypeResponse(
        Long id,
        String name,
        Boolean isGlutenFree,
        BigDecimal extraPrice,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
        ) {
}
