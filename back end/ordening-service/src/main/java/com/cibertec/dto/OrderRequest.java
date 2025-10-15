package com.cibertec.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.cibertec.enums.DeliveryMethod;
import com.cibertec.enums.OrderStatus;
import com.cibertec.enums.PaymentStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record  OrderRequest (
    
      @NotBlank(message = "Order number is required")
        String orderNumber,

        Long customerId,

        Long addressId,

        @NotNull(message = "Order status is required")
        OrderStatus status,

        @NotNull(message = "Delivery method is required")
        DeliveryMethod deliveryMethod,

        String notes,

        @NotNull(message = "Subtotal is required")
        BigDecimal subtotal,

        @NotNull(message = "Tax is required")
        BigDecimal tax,

        @NotNull(message = "Delivery fee is required")
        BigDecimal deliveryFee,

        @NotNull(message = "Discount total is required")
        BigDecimal discountTotal,

        @NotNull(message = "Total is required")
        BigDecimal total,

        @NotNull(message = "Payment status is required")
        PaymentStatus paymentStatus,

        LocalDateTime placedAt
){}
