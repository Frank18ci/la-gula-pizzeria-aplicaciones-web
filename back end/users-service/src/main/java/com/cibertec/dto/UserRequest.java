package com.cibertec.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UserRequest(
        @NotBlank(message = "El email no puede estar vacío")
        @Email(message = "El email debe tener un formato válido")
        String email,
        @NotBlank(message = "El password no puede estar vacío")
        String password,
        @NotBlank(message = "El fullName no puede estar vacío")
        String fullName,
        String phone,
        @NotBlank(message = "El status no puede estar vacío")
        String status
) {
}
