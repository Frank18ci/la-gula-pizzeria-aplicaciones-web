package com.cibertec.util;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import com.cibertec.dto.OrderRequest;
import com.cibertec.dto.OrderResponse;
import com.cibertec.model.Order;



@Mapper(componentModel = "spring")
public interface OrderMapper {


    @Mapping(target = "id", ignore = true)
    Order toEntity(OrderRequest orderRequest);

    OrderResponse toDto(Order order);

    List<OrderResponse> toDtoList(List<Order> orders);

    
}
