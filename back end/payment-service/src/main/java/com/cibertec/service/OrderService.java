package com.cibertec.service;

import com.cibertec.dto.OrderRequest;
import com.cibertec.dto.OrderResponse;

import java.util.List;

public interface OrderService {
    List<OrderResponse> getAllOrders();
    OrderResponse getOrderById(Long id);
    OrderResponse createOrder(OrderRequest orderRequest);
    OrderResponse updateOrder(Long id, OrderRequest orderRequest);
    void deleteOrder(Long id);
}
