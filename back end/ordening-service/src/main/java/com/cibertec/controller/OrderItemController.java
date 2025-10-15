package com.cibertec.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.dto.OrderItemRequest;
import com.cibertec.service.OrderItemService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/order_items")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

     @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(orderItemService.getAllOrderItems());
    }

     @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(orderItemService.getOrderItemById(id));
    }

     @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid OrderItemRequest orderItemRequest) {
        return ResponseEntity.ok(orderItemService.createOrderItem(orderItemRequest));
    }

      @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid OrderItemRequest orderItemRequest) {
        return ResponseEntity.ok(orderItemService.updateOrderItem (id, orderItemRequest));
    }

     @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.noContent().build();
    }
     
    
}
