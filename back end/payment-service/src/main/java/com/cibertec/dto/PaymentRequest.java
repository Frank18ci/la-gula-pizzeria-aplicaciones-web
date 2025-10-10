package com.cibertec.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record PaymentRequest(
        @NotNull(message = "El orderId no puede estar vacío")
        Long orderId,
        @NotNull(message = "El amount no puede estar vacío")
        BigDecimal amount,
        @NotBlank(message = "El currency no puede estar vacío")
        String currency,
        @NotNull(message = "El paymentProvider no puede estar vacío")
        Long paymentProviderId,
        @NotNull(message = "El paymentStatus no puede estar vacío")
        Long paymentStatusId,
        @NotBlank(message = "El externalId no puede estar vacío")
        String externalId,
        LocalDateTime processedAt
) {

}
