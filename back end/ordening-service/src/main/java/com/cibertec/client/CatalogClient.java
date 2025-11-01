package com.cibertec.client;

import com.cibertec.client.dto.DoughTypeResponse;
import com.cibertec.client.dto.PizzaResponse;
import com.cibertec.client.dto.SizeResponse;
import com.cibertec.client.dto.ToppingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "catalog-service")
public interface CatalogClient {
    @GetMapping("/dough-types/{id}")
    DoughTypeResponse findDoughTypeById(@PathVariable Long id);
    @GetMapping("/pizzas/{id}")
    PizzaResponse getPizzaById(@PathVariable Long id);
    @GetMapping("/sizes/{id}")
    SizeResponse findSizeById(@PathVariable Long id);
    @GetMapping("/toppings/{id}")
    ToppingResponse findToppingById(@PathVariable Long id);
}
