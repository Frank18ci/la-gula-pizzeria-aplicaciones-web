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

import com.cibertec.dto.OrderItemToppingRequest;
import com.cibertec.service.OrderItemToppingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/order_item_toppings")
@RequiredArgsConstructor
public class OrderItemToppingController {

      private final OrderItemToppingService orderItemToppingService;


        @GetMapping
        public ResponseEntity<?> findAll() {
            return ResponseEntity.ok(orderItemToppingService.getAllOrderItemToppings());
        }

        @GetMapping("/{id}")
        public ResponseEntity<?> findById(Long id) {
            return ResponseEntity.ok(orderItemToppingService.getOrderItemToppingById(id));
        }

        @PostMapping
        public ResponseEntity<?> create(@RequestBody @Valid OrderItemToppingRequest orderItemToppingRequest) {
            return ResponseEntity.ok(orderItemToppingService.createOrderItemTopping(orderItemToppingRequest));
        }

        @PutMapping("/{id}")
        public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid OrderItemToppingRequest orderItemToppingRequest) {
            return ResponseEntity.ok(orderItemToppingService.updateOrderItemTopping(id, orderItemToppingRequest));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<?> delete(@PathVariable Long id) {
            orderItemToppingService.deleteOrderItemTopping(id);
            return ResponseEntity.noContent().build();
        }

}