package com.cibertec.dto;

import lombok.Builder;

@Builder
public record PaymentStatusResponse(
        Long id,
        String name
) {
}
