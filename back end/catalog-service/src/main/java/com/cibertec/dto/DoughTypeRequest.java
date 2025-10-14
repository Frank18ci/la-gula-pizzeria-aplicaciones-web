package com.cibertec.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Builder
public record DoughTypeRequest(
        @NotBlank(message = "Name es obligatorio")
        @Length(max = 50, message = "Name debe tener máximo 50 caracteres")
        String name,
        @NotNull(message = "isGlutenFree es obligatorio")
        Boolean isGlutenFree,
        @NotNull(message = "extraPrice es obligatorio")
        @PositiveOrZero(message = "extraPrice debe ser cero o un valor positivo")
        BigDecimal extraPrice,
        @NotNull(message = "active es obligatorio")
        Boolean active
) {
}
