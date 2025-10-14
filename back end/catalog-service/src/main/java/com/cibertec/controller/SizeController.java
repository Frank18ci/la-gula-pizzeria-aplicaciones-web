package com.cibertec.controller;

import com.cibertec.dto.SizeRequest;
import com.cibertec.service.SizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sizes")
public class SizeController {
    private final SizeService sizeService;
    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(sizeService.getSizes());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(sizeService.getSizeById(id));
    }
    @PostMapping
    public ResponseEntity<?> create(@RequestBody SizeRequest sizeRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sizeService.createSize(sizeRequest));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody SizeRequest sizeRequest) {
        return ResponseEntity.ok(sizeService.updateSize(id, sizeRequest));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        sizeService.deleteSize(id);
        return ResponseEntity.noContent().build();
    }
}
