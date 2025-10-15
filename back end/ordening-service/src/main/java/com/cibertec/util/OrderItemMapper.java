package com.cibertec.util;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cibertec.dto.OrderItemRequest;
import com.cibertec.dto.OrderItemResponse;
import com.cibertec.model.OrderItem;

@Mapper(componentModel = "spring", uses = {OrderMapper.class})
public interface OrderItemMapper {
    @Mapping(source = "orderId", target = "order.id")
    @Mapping(target = "id", ignore = true)
    OrderItem toEntity(OrderItemRequest orderItemRequest);

    OrderItemResponse toDto(OrderItem orderItem);

    List<OrderItemResponse> toDtoList(List<OrderItem> orderItems);
}