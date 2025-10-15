package com.cibertec.util;

import java.util.List;

import org.mapstruct.Mapper;

import com.cibertec.dto.OrderItemToppingRequest;
import com.cibertec.dto.OrderItemToppingResponse;
import com.cibertec.model.OrderItemTopping;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderItemToppingMapper {
    @Mapping(source = "orderItemId", target = "orderItem.id")
    @Mapping(target = "id", ignore = true)
    OrderItemTopping toEntity(OrderItemToppingRequest orderItemToppingRequest);

    OrderItemToppingResponse toDto(OrderItemTopping orderItemTopping);

    List<OrderItemToppingResponse> toDtoList(List<OrderItemTopping> orderItemToppings);
    
}
