package com.cibertec.util;

import java.util.List;

import org.mapstruct.Mapper;

import com.cibertec.dto.OrderItemToppingRequest;
import com.cibertec.dto.OrderItemToppingResponse;
import com.cibertec.model.OrderItemTopping;

@Mapper(componentModel = "spring")
public interface OrderItemToppingMapper {

    OrderItemTopping toEntity(OrderItemToppingRequest orderItemToppingRequest);

    OrderItemToppingResponse toDto(OrderItemTopping orderItemTopping);

    List<OrderItemToppingResponse> toDtoList(List<OrderItemTopping> orderItemToppings);
    
}
