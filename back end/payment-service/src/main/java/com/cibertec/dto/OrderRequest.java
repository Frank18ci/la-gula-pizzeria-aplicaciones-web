package com.cibertec.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderRequest(
        @NotBlank(message = "El customerId no puede estar vacío")
        String customerName,
        @NotNull(message = "El totalAmount no puede estar vacío")
        BigDecimal totalAmount
) {
}
