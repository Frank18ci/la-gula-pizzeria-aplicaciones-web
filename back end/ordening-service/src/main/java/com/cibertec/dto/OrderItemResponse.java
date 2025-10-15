package com.cibertec.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record OrderItemResponse(

    Long id,

    Long pizzaId,

    Long sizeId,

    Long doughTypeId,

    Integer quantity,

    BigDecimal unitPrice,

    BigDecimal lineTotal,

    String note,

    LocalDateTime createdAt,

    LocalDateTime updatedAt,
    
    List<OrderItemToppingResponse> toppings
) {}