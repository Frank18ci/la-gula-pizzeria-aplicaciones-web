package com.cibertec.service;

import com.cibertec.dto.PizzaRequest;
import com.cibertec.dto.PizzaResponse;

import java.math.BigDecimal;
import java.util.List;

public interface PizzaService {
    List<PizzaResponse> getPizzas();

    PizzaResponse getPizzaById(Long id);

    PizzaResponse createPizza(PizzaRequest pizzaRequest);

    PizzaResponse updatePizza(Long id, PizzaRequest pizzaRequest);

    void deletePizza(Long id);

    List<PizzaResponse> getAllPizzasByPriceBetweenAndSizeIdAndDoughTypeId(BigDecimal minPrice, BigDecimal maxPrice, Long sizeId, Long toppingId);

    List<PizzaResponse> getAllPizzasByName(String name);
}
