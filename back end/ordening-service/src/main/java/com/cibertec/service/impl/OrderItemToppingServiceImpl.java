package com.cibertec.service.impl;

import com.cibertec.client.CatalogClient;
import com.cibertec.dto.OrderItemToppingRequest;
import com.cibertec.dto.OrderItemToppingResponse;
import com.cibertec.model.OrderItemTopping;
import com.cibertec.repository.OrderItemToppingRepository;
import com.cibertec.service.OrderItemToppingService;
import com.cibertec.util.OrderItemToppingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderItemToppingServiceImpl implements OrderItemToppingService {

    private final OrderItemToppingMapper orderItemToppingMapper;

    private final OrderItemToppingRepository orderItemToppingRepository;

    private final CatalogClient catalogClient;

    @Override
    public List<OrderItemToppingResponse> getAllOrderItemToppings() {
        return orderItemToppingMapper.toDtoList(orderItemToppingRepository.findAll());
    }

    @Override
    public OrderItemToppingResponse getOrderItemToppingById(Long id) {
        return orderItemToppingMapper.toDto(orderItemToppingRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Order Item Topping not found with id: " + id)
        ));
    }

    @Override
    public OrderItemToppingResponse createOrderItemTopping(OrderItemToppingRequest orderItemToppingRequest) {
        catalogClient.findToppingById(orderItemToppingRequest.toppingId());
        return orderItemToppingMapper.toDto(orderItemToppingRepository.save
                (orderItemToppingMapper.toEntity(orderItemToppingRequest)));
    }

    @Override
    public OrderItemToppingResponse updateOrderItemTopping(Long id, OrderItemToppingRequest orderItemToppingRequest) {
        OrderItemTopping orderItemToppingFound = orderItemToppingRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Order Item Topping not found with id: " + id)
        );

        catalogClient.findToppingById(orderItemToppingRequest.toppingId());

        orderItemToppingFound.setToppingId(orderItemToppingRequest.toppingId());
        orderItemToppingFound.setAction(orderItemToppingRequest.action());
        orderItemToppingFound.setQuantity(orderItemToppingRequest.quantity());
        orderItemToppingFound.setPriceDelta(orderItemToppingRequest.priceDelta());

        return orderItemToppingMapper.toDto(orderItemToppingRepository.save(orderItemToppingFound));
    }


    @Override
    public void deleteOrderItemTopping(Long id) {
        OrderItemTopping orderItemToppingFound = orderItemToppingRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Order Item Topping not found with id: " + id)
        );
        orderItemToppingRepository.delete(orderItemToppingFound);
    }
}
