package com.cibertec.controller;

import com.cibertec.dto.OrderRequest;
import com.cibertec.dto.PaymentRequest;
import com.cibertec.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(Long id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid PaymentRequest paymentRequest) {
        return ResponseEntity.ok(paymentService.createPayment(paymentRequest));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid PaymentRequest paymentRequest) {
        return ResponseEntity.ok(paymentService.updatePayment(id, paymentRequest));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}
