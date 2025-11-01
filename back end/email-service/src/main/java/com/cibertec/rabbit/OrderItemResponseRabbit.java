package com.cibertec.rabbit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemResponseRabbit implements Serializable {
    private String pizzaName;
    private String sizeName;
    private String doughTypeName;
    private Integer quantity;
    private List<OrderItemToppingResponseRabbit> orderItemToppings;
}
