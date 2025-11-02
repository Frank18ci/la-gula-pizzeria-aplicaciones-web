package com.cibertec.controller;

import com.cibertec.dto.ToppingRequest;
import com.cibertec.service.ToppingService;
import com.cibertec.storage.ImageStorageService;
import com.cibertec.storage.TypeStorageEnum;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/toppings")
public class ToppingController {
    private final ToppingService toppingService;
    private final ImageStorageService imageStorageService;

    @GetMapping
    public ResponseEntity<?> getAllToppings() {
        return ResponseEntity.ok(toppingService.getToppings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getToppingById(@PathVariable Long id) {
        return ResponseEntity.ok(toppingService.getToppingById(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createTopping(@ModelAttribute @Valid ToppingRequest toppingRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toppingService.createTopping(toppingRequest));
    }

    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateTopping(@PathVariable Long id, @ModelAttribute @Valid ToppingRequest toppingRequest) {
        return ResponseEntity.ok(toppingService.updateTopping(id, toppingRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTopping(@PathVariable Long id) {
        toppingService.deleteTopping(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("imagen/{image}")
    public ResponseEntity<?> getImage(@PathVariable String image) {
        return imageStorageService.obtainImage(image, TypeStorageEnum.TOPPING);
    }

    @GetMapping("search")
    public ResponseEntity<?> searchToppings(@RequestParam String name) {
        return ResponseEntity.ok(toppingService.searchToppingsByName(name));
    }
}
