package com.cibertec.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record PaymentResponse(
        Long id,
        OrderResponse order,
        BigDecimal amount,
        String currency,
        PaymentProviderResponse paymentProvider,
        PaymentStatusResponse paymentStatus,
        String externalId,
        LocalDateTime processedAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

}
