package com.cibertec.service;
import java.util.List;

import com.cibertec.dto.OrderItemToppingRequest;
import com.cibertec.dto.OrderItemToppingResponse;

public interface OrderItemToppingService {
    List<OrderItemToppingResponse> getAllOrderItemToppings();
    OrderItemToppingResponse getOrderItemToppingById(Long id);
    OrderItemToppingResponse createOrderItemTopping(OrderItemToppingRequest orderItemToppingRequest);
    OrderItemToppingResponse updateOrderItemTopping(Long id, OrderItemToppingRequest orderItemToppingRequest);
    void deleteOrderItemTopping(Long id);
}
