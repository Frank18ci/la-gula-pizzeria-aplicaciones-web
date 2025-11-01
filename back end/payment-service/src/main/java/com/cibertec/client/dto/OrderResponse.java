package com.cibertec.client.dto;

import com.cibertec.client.dto.enums.DeliveryMethod;
import com.cibertec.client.dto.enums.OrderStatus;
import com.cibertec.client.dto.enums.PaymentStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record OrderResponse(

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

        LocalDateTime updatedAt

) {
}
