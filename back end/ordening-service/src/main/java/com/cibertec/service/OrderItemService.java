package com.cibertec.service;

import java.util.List;

import com.cibertec.dto.OrderItemRequest;
import com.cibertec.dto.OrderItemResponse;

public interface OrderItemService {

    List<OrderItemResponse> getAllOrderItems();

    OrderItemResponse getOrderItemById(Long id);

    OrderItemResponse createOrderItem(OrderItemRequest orderItemRequest);

    OrderItemResponse updateOrderItem(Long id, OrderItemRequest orderItemRequest);
    
    void deleteOrderItem(Long id);
}
