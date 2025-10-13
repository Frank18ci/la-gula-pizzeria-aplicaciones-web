package com.cibertec.dto;

import java.time.LocalDateTime;

import com.cibertec.enums.DeliveryMethod;
import com.cibertec.enums.DeliveryStatus;

public record DeliveryResponse(

        Long id,

        Long orderId,

        Long addressId,

        DeliveryMethod method,

        DeliveryStatus status,

        String driverName,

        String driverPhone,

        LocalDateTime deliveredAt,

        String instructions,

        LocalDateTime createdAt,
        
        LocalDateTime updatedAt
          
){}
