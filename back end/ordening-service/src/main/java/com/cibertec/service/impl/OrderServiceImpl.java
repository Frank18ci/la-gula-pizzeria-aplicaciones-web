package com.cibertec.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cibertec.dto.OrderRequest;
import com.cibertec.dto.OrderResponse;
import com.cibertec.model.Order;
import com.cibertec.repository.OrderRepository;
import com.cibertec.service.OrderService;
import com.cibertec.util.OrderMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public List<OrderResponse> getAllOrders() {
       return orderMapper.toDtoList( orderRepository.findAll());
    }

    @Override
    public OrderResponse getOrderById(Long id) {
          return orderMapper.toDto(orderRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Order not found with id: " + id)
        ));
    }

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
       return orderMapper.toDto(orderRepository.save(orderMapper.toEntity(orderRequest)));
    }

    @Override
    public OrderResponse updateOrder(Long id, OrderRequest orderRequest) {
          Order orderFound = orderRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Order not found with id: " + id)
        );
   
           orderFound.setOrderNumber(orderRequest.orderNumber());
           orderFound.setCustomerId(orderRequest.customerId());
           orderFound.setAddressId(orderRequest.addressId());
           orderFound.setStatus(orderRequest.status());
           orderFound.setDeliveryMethod(orderRequest.deliveryMethod());
           orderFound.setNotes(orderRequest.notes());
           orderFound.setSubtotal(orderRequest.subtotal());
           orderFound.setTax(orderRequest.tax());
           orderFound.setDeliveryFee(orderRequest.deliveryFee());
           orderFound.setDiscountTotal(orderRequest.discountTotal());
         orderFound.setTotal(orderRequest.total());
          orderFound.setPaymentStatus(orderRequest.paymentStatus());
             //fecha quizas
          orderFound.setUpdatedAt(LocalDateTime.now()); 

        return orderMapper.toDto(orderRepository.save(orderFound));
    }

    @Override
    public void deleteOrder(Long id) {
          Order orderFound = orderRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Order not found with id: " + id)
        );
        orderRepository.delete(orderFound);
    }

   




    
}
