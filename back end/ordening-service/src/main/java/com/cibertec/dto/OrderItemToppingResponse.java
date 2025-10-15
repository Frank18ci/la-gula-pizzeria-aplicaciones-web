package com.cibertec.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record OrderItemToppingResponse(

    Long id,

    Long toppingId,

    String action,

    Integer quantity,

    BigDecimal priceDelta,
    
    LocalDateTime createdAt
) {}