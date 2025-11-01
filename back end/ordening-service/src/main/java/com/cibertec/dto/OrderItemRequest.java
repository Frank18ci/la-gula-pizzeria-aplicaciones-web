package com.cibertec.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;


@Builder
public record OrderItemRequest(


        @NotNull(message = "Pizza ID is required")
        Long pizzaId,

        @NotNull(message = "Size ID is required")
        Long sizeId,

        @NotNull(message = "Dough type ID is required")
        Long doughTypeId,

        @NotNull(message = "Quantity is required")
        Integer quantity,

        @NotNull(message = "Unit price is required")
        BigDecimal unitPrice,

        @NotNull(message = "Line total is required")
        BigDecimal lineTotal,
        @NotNull(message = "Order ID is required")
        Long orderId,

        String note


) {
}