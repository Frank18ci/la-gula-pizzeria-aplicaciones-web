package com.cibertec.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cibertec.dto.OrderItemRequest;
import com.cibertec.dto.OrderItemResponse;
import com.cibertec.model.OrderItem;
import com.cibertec.repository.OrderItemRepository;
import com.cibertec.service.OrderItemService;
import com.cibertec.util.OrderItemMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl  implements  OrderItemService{

    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    public List<OrderItemResponse> getAllOrderItems() {
           return orderItemMapper.toDtoList(orderItemRepository.findAll());
    }

    @Override
    public OrderItemResponse getOrderItemById(Long id) {
         return orderItemMapper.toDto(orderItemRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Order Item not found with id: " + id)));
    }

 @Override
    public OrderItemResponse createOrderItem(OrderItemRequest orderItemRequest) {
        return orderItemMapper.toDto(orderItemRepository.save(
                orderItemMapper.toEntity(orderItemRequest)
        ));
    }


    @Override
    public OrderItemResponse updateOrderItem(Long id, OrderItemRequest orderItemRequest) {
    OrderItem orderItemFound = orderItemRepository.findById(id).orElseThrow(
        () -> new RuntimeException("Order Item not found with id: " + id)
    );

    orderItemFound.setPizzaId(orderItemRequest.pizzaId());
    orderItemFound.setSizeId(orderItemRequest.sizeId());
    orderItemFound.setDoughTypeId(orderItemRequest.doughTypeId());
    orderItemFound.setQuantity(orderItemRequest.quantity());
    orderItemFound.setUnitPrice(orderItemRequest.unitPrice());
    orderItemFound.setLineTotal(orderItemRequest.lineTotal());
    orderItemFound.setNote(orderItemRequest.note());
    
    // orderItemFound.setToppings(orderItemRequest.toppingIds());

    return orderItemMapper.toDto(orderItemRepository.save(orderItemFound));
}


    @Override
    public void deleteOrderItem(Long id) {
        OrderItem orderItemFound = orderItemRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Order Item not found with id: " + id));
        orderItemRepository.delete(orderItemFound);
    }
    
}
