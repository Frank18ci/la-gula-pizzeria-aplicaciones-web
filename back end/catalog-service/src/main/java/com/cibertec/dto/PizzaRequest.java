package com.cibertec.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record PizzaRequest(
        @NotBlank(message = "El name no puede estar vacío")
        @Length(max = 120, message = "El name debe tener máximo 120 caracteres")
        String name,
        @Length(max = 120, message = "El description debe tener máximo 120 caracteres")
        String description,
        @NotNull(message = "El basePrice no puede estar vacío")
        BigDecimal basePrice,
        @NotNull(message = "El active no puede estar vacío")
        Boolean active,
        List<Long> toppingIds,
        List<Long> sizeIds,
        MultipartFile image
) {
}
