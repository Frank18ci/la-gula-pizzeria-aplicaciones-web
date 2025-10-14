package com.cibertec.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record SizeResponse(
        Long id,
        String name,
        BigDecimal diameterCm,
        BigDecimal priceMultiplier,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
