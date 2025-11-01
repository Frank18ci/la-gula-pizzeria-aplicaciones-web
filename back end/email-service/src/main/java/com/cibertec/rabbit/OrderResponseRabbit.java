package com.cibertec.rabbit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseRabbit implements Serializable {
    private Long orderId;
    private String orderStatus;
    private BigDecimal amount;
    private String customerName;
    private String customerEmail;
    private List<OrderItemResponseRabbit> orderItems;
}
