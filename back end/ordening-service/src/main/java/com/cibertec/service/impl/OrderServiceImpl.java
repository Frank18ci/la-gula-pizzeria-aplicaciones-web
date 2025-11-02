package com.cibertec.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import com.cibertec.client.*;
import com.cibertec.client.dto.CustomerResponse;
import com.cibertec.client.dto.UserResponse;
import com.cibertec.producer.OrderProducer;
import com.cibertec.rabbit.OrderItemResponseRabbit;
import com.cibertec.rabbit.OrderItemToppingResponseRabbit;
import com.cibertec.rabbit.OrderResponseRabbit;
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

    private final CustomerClient customerClient;
    private final UserClient userClient;
    private final CatalogClient catalogClient;

    private final OrderProducer orderProducer;

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderMapper.toDtoList(orderRepository.findAll());
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        return orderMapper.toDto(orderRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Order not found with id: " + id)
        ));
    }

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        customerClient.findById(orderRequest.customerId());
        Order order = orderRepository.save(orderMapper.toEntity(orderRequest));
        return orderMapper.toDto(order);
    }

    @Override
    public OrderResponse updateOrder(Long id, OrderRequest orderRequest) {
        Order orderFound = orderRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Order not found with id: " + id)
        );

        customerClient.findById(orderRequest.customerId());

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

    @Override
    public void notifyOrder(Long id) {
        Order orderFound = orderRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Order not found with id: " + id)
        );
        CustomerResponse customerResponse = customerClient.findById(orderFound.getCustomerId());
        UserResponse userResponse = userClient.findById(customerResponse.userId());
        orderProducer.sendOrder(
                OrderResponseRabbit.builder()
                        .customerName(userResponse.fullName())
                        .customerEmail(userResponse.email())
                        .orderStatus(orderFound.getStatus().name())
                        .orderId(orderFound.getId())
                        .amount(orderFound.getTotal())
                        .orderItems(orderFound.getOrderItems().stream().map(oi ->
                                        OrderItemResponseRabbit.builder()
                                                .pizzaName(catalogClient.getPizzaById(oi.getPizzaId()).name())
                                                .sizeName(catalogClient.findSizeById(oi.getSizeId()).name())
                                                .doughTypeName(catalogClient.findDoughTypeById(oi.getDoughTypeId()).name())
                                                .quantity(oi.getQuantity())
                                                .orderItemToppings(oi.getToppings().stream().map(t ->
                                                                OrderItemToppingResponseRabbit.builder()
                                                                        .toppingName(catalogClient.findToppingById(t.getToppingId()).name())
                                                                        .quantity(t.getQuantity())
                                                                        .build())
                                                        .toList())
                                                .build())
                                .toList())
                        .build()
        );

    }


}
