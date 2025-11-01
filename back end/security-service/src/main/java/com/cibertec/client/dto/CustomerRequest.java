package com.cibertec.client.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CustomerRequest(
        @NotNull(message = "userId no puede ser nulo")
        Long userId,
        @NotNull(message = "loyaltyPoints no puede ser nulo")
        Integer loyaltyPoints,
        @NotNull(message = "birthDate no puede ser nulo")
        LocalDateTime birthDate,
        @NotBlank(message = "notes no puede estar vacio")
        String notes
) {
}
