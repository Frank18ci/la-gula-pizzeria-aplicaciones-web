package com.cibertec.controller;

import com.cibertec.dto.DoughTypeRequest;
import com.cibertec.service.DoughTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dough-types")
public class DoughTypeController {
    private final DoughTypeService doughTypeService;
    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(doughTypeService.getAllDoughTypes());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(doughTypeService.getDoughTypeById(id));
    }
    @PostMapping
    public ResponseEntity<?> create(@RequestBody DoughTypeRequest doughTypeRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(doughTypeService.createDoughType(doughTypeRequest));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody DoughTypeRequest doughTypeRequest) {
        return ResponseEntity.ok(doughTypeService.updateDoughType(id, doughTypeRequest));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        doughTypeService.deleteDoughType(id);
        return ResponseEntity.noContent().build();
    }
}
