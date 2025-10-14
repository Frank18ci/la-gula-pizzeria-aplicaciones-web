package com.cibertec.util;

import com.cibertec.dto.PizzaRequest;
import com.cibertec.dto.PizzaResponse;
import com.cibertec.model.Pizza;
import com.cibertec.model.Topping;
import com.cibertec.repository.ToppingRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ToppingMapper.class})
public interface PizzaMapper {
    @Mapping(source = "toppingIds", target = "toppings")
    Pizza toEntity(PizzaRequest pizzaRequest, @Context ToppingRepository toppingRepository);
    PizzaResponse toDto(Pizza pizza);
    List<PizzaResponse> toDtoList(List<Pizza> pizzas);

    default List<Topping> map(List<Long> toppingIds, @Context ToppingRepository toppingRepository) {
        if(toppingIds == null || toppingIds.isEmpty()) {
            return List.of();
        }
        return toppingRepository.findAllById(toppingIds);
    }
}
