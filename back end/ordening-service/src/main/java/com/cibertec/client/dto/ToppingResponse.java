package com.cibertec.client.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record ToppingResponse(
        Long id,
        String name,
        String category,
        Boolean isVegetarian,
        BigDecimal basePrice,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String image
) {
}
