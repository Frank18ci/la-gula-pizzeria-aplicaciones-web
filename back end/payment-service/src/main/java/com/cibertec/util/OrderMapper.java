package com.cibertec.util;

import com.cibertec.dto.OrderRequest;
import com.cibertec.dto.OrderResponse;
import com.cibertec.model.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toEntity(OrderRequest orderRequest);
    OrderResponse toDto(Order order);
    List<OrderResponse> toDtoList(List<Order> orders);
}
