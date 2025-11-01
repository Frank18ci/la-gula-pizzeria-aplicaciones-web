package com.cibertec.rabbit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemToppingResponseRabbit implements Serializable {
    private String toppingName;
    private Integer quantity;
}
