package com.cibertec.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.cibertec.model.OrderItem;
import com.cibertec.model.OrderItemTopping;
import lombok.Builder;

@Builder
public record OrderItemToppingResponse(

        Long id,

        Long toppingId,

        String action,

        Integer quantity,

        BigDecimal priceDelta,

        LocalDateTime createdAt,
        OrderItemResponse orderItem
) {
}