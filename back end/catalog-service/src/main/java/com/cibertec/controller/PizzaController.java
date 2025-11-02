package com.cibertec.controller;

import com.cibertec.dto.PizzaRequest;
import com.cibertec.service.PizzaService;
import com.cibertec.storage.ImageStorageService;
import com.cibertec.storage.TypeStorageEnum;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


@RestController
@RequiredArgsConstructor
@RequestMapping("/pizzas")
public class PizzaController {
    private final PizzaService pizzaService;
    private final ImageStorageService imageStorageService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(pizzaService.getPizzas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(pizzaService.getPizzaById(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> create(@ModelAttribute @Valid PizzaRequest pizzaRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pizzaService.createPizza(pizzaRequest));
    }

    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(@PathVariable Long id, @ModelAttribute @Valid PizzaRequest pizzaRequest) {
        return ResponseEntity.ok(pizzaService.updatePizza(id, pizzaRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        pizzaService.deletePizza(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("imagen/{image}")
    public ResponseEntity<?> getImage(@PathVariable String image) {
        return imageStorageService.obtainImage(image, TypeStorageEnum.PIZZA);
    }

    @GetMapping("searchOptions")
    public ResponseEntity<?> geAllPizzasByPriceBetweenAndSizeIdAndDoughTypeId(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice,
            @RequestParam Long sizeId,
            @RequestParam Long toppingId) {
        return ResponseEntity.ok(pizzaService.getAllPizzasByPriceBetweenAndSizeIdAndDoughTypeId(minPrice, maxPrice, sizeId, toppingId));
    }

    @GetMapping("search")
    public ResponseEntity<?> getAllPizzasByName(
            @RequestParam String name) {
        return ResponseEntity.ok(pizzaService.getAllPizzasByName(name));
    }
}
