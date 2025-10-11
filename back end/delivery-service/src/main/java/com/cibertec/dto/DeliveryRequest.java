package com.cibertec.dto;

import com.cibertec.enums.DeliveryMethod;
import com.cibertec.enums.DeliveryStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DeliveryRequest(
        
         @NotNull(message = "El ID de la orden es obligatorio")
        Long orderId,

        @NotNull(message = "El ID de la dirección es obligatorio")
        Long addressId,

        @NotNull(message = "El método de entrega es obligatorio")
        DeliveryMethod method,

        @NotNull(message = "El estado de la entrega es obligatorio")
        DeliveryStatus status,

        @NotBlank(message = "El nombre del conductor es obligatorio")
        String driverName,

        @NotBlank(message = "El teléfono del conductor es obligatorio")
        String driverPhone,

        @NotBlank(message = "Las instrucciones son obligatorias")
        String instructions
    
) {}
