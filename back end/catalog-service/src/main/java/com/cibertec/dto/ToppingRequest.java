package com.cibertec.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Builder
public record ToppingRequest(
        @NotBlank(message = "Name es obligatorio")
        @Length(max = 80, message = "Name debe tener máximo 80 caracteres")
        String name,
        @Length(max = 50, message = "Description debe tener máximo 50 caracteres")
        String category,
        @NotNull(message = "isVegetarian no puede ser nulo")
        Boolean isVegetarian,
        @NotNull(message = "Base Price no puede ser nulo")
        BigDecimal basePrice,
        @NotNull(message = "active no puede ser nulo")
        Boolean active,
        MultipartFile image
) {
}
