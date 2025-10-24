package com.cibertec.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record PizzaResponse(
        Long id,
        String name,
        String description,
        BigDecimal basePrice,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<ToppingResponse> toppings,
        List<SizeResponse> sizes,
        String image
) {
}
