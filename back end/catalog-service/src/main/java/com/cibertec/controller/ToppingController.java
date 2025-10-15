package com.cibertec.controller;

import com.cibertec.dto.ToppingRequest;
import com.cibertec.service.ToppingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/toppings")
public class ToppingController {
    private final ToppingService toppingService;
    @GetMapping
    public ResponseEntity<?> getAllToppings() {
        return ResponseEntity.ok(toppingService.getToppings());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getToppingById(@PathVariable Long id) {
        return ResponseEntity.ok(toppingService.getToppingById(id));
    }
    @PostMapping
    public ResponseEntity<?> createTopping(@RequestBody @Valid ToppingRequest toppingRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toppingService.createTopping(toppingRequest));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTopping(@PathVariable Long id, @RequestBody @Valid ToppingRequest toppingRequest) {
        return ResponseEntity.ok(toppingService.updateTopping(id, toppingRequest));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTopping(@PathVariable Long id) {
        toppingService.deleteTopping(id);
        return ResponseEntity.noContent().build();
    }
}
