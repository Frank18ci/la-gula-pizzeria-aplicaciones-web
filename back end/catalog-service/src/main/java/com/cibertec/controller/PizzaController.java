package com.cibertec.controller;

import com.cibertec.dto.PizzaRequest;
import com.cibertec.service.PizzaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pizzas")
public class PizzaController {
    private final PizzaService pizzaService;
    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(pizzaService.getPizzas());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(pizzaService.getPizzaById(id));
    }
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid PizzaRequest pizzaRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pizzaService.createPizza(pizzaRequest));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid PizzaRequest pizzaRequest) {
        return ResponseEntity.ok(pizzaService.updatePizza(id, pizzaRequest));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        pizzaService.deletePizza(id);
        return ResponseEntity.noContent().build();
    }
}
