package com.cibertec.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.cibertec.enums.DeliveryMethod;
import com.cibertec.enums.OrderStatus;
import com.cibertec.enums.PaymentStatus;
import lombok.Builder;

@Builder
public  record OrderResponse (

        Long id,  

        String orderNumber,

        Long customerId,

        Long addressId,

        OrderStatus status,

        DeliveryMethod deliveryMethod,

        String notes,

        BigDecimal subtotal,

        BigDecimal tax,

        BigDecimal deliveryFee,

        BigDecimal discountTotal,

        BigDecimal total,

        PaymentStatus paymentStatus,

        LocalDateTime placedAt,

        LocalDateTime updatedAt,
        
        List<OrderItemResponse> orderItems
    
){}
