package com.cibertec.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cibertec.dto.OrderRequest;
import com.cibertec.dto.OrderResponse;
import com.cibertec.model.Order;
import com.cibertec.repository.OrderRepository;
import com.cibertec.service.OrderService;
import com.cibertec.util.OrderMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper ordersMapper;

    private final OrderRepository orderRepository;
    
    @Override
    public List<OrderResponse> getAllOrders() {
        return ordersMapper.toDtoList( orderRepository.findAll());
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        return ordersMapper.toDto( orderRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Order not found with id: " + id)
        ));
    }

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        return ordersMapper.toDto(orderRepository.save(ordersMapper.toEntity(orderRequest)));
    }

    @Override
    public OrderResponse updateOrder(Long id, OrderRequest orderRequest) {
        Order orderFound = orderRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Order not found with id: " + id)
        );
        orderFound.setCustomerName(orderRequest.customerName());
        orderFound.setTotalAmount(orderRequest.totalAmount());
        return ordersMapper.toDto(orderRepository.save(orderFound));
    }

    @Override
    public void deleteOrder(Long id) {
        Order orderFound = orderRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Order not found with id: " + id)
        );
        orderRepository.delete(orderFound);
    }
    
}
