package com.cibertec.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderResponse(
        Long id,
        String customerName,
        BigDecimal totalAmount
) {
}
