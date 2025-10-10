package com.cibertec.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record PaymentProviderRequest(
        @NotBlank(message = "El name no puede estar vacío")
        String name
) {
}
