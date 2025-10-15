package com.cibertec.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record OrderItemToppingRequest(
    
    @NotNull(message = "Topping ID is required")
    Long toppingId,

    @NotNull(message = "Action is required")
    String action,

    @NotNull(message = "Quantity is required")
    Integer quantity,

    @NotNull(message = "Price delta is required")
    BigDecimal priceDelta
) {}