package com.cibertec.util;

import com.cibertec.dto.ToppingRequest;
import com.cibertec.dto.ToppingResponse;
import com.cibertec.model.Topping;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ToppingMapper {
    Topping toEntity(ToppingRequest toppingRequest);
    ToppingResponse toDto(Topping topping);
    List<ToppingResponse> toDtoList(List<Topping> toppings);
}
