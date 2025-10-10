package com.cibertec.dto;

import lombok.Builder;

@Builder
public record PaymentProviderResponse(
        Long id,
        String name
) {
}
