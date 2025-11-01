package com.cibertec.service.impl;

import com.cibertec.client.CatalogClient;
import com.cibertec.dto.OrderItemRequest;
import com.cibertec.dto.OrderItemResponse;
import com.cibertec.model.OrderItem;
import com.cibertec.repository.OrderItemRepository;
import com.cibertec.service.OrderItemService;
import com.cibertec.util.OrderItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    private final CatalogClient catalogClient;

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
        catalogClient.getPizzaById(orderItemRequest.pizzaId());
        catalogClient.findSizeById(orderItemRequest.sizeId());
        catalogClient.findDoughTypeById(orderItemRequest.doughTypeId());

        return orderItemMapper.toDto(orderItemRepository.save(
                orderItemMapper.toEntity(orderItemRequest)
        ));
    }


    @Override
    public OrderItemResponse updateOrderItem(Long id, OrderItemRequest orderItemRequest) {
        OrderItem orderItemFound = orderItemRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Order Item not found with id: " + id)
        );

        catalogClient.getPizzaById(orderItemRequest.pizzaId());
        catalogClient.findSizeById(orderItemRequest.sizeId());
        catalogClient.findDoughTypeById(orderItemRequest.doughTypeId());

        orderItemFound.setPizzaId(orderItemRequest.pizzaId());
        orderItemFound.setSizeId(orderItemRequest.sizeId());
        orderItemFound.setDoughTypeId(orderItemRequest.doughTypeId());
        orderItemFound.setQuantity(orderItemRequest.quantity());
        orderItemFound.setUnitPrice(orderItemRequest.unitPrice());
        orderItemFound.setLineTotal(orderItemRequest.lineTotal());
        orderItemFound.setNote(orderItemRequest.note());

        return orderItemMapper.toDto(orderItemRepository.save(orderItemFound));
    }


    @Override
    public void deleteOrderItem(Long id) {
        OrderItem orderItemFound = orderItemRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Order Item not found with id: " + id));
        orderItemRepository.delete(orderItemFound);
    }

}
