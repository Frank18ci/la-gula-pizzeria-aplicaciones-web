package com.cibertec.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record SizeRequest(
        @NotBlank(message = "Name es obligatorio")
        String name,
        BigDecimal diameterCm,
        @NotNull(message = "PriceMultiplier es obligatorio")
        @PositiveOrZero(message = "PriceMultiplier debe ser cero o positivo")
        BigDecimal priceMultiplier,
        @NotNull(message = "Active es obligatorio")
        Boolean active
) {
}
