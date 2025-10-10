package com.cibertec.controller;

import com.cibertec.dto.PaymentStatusRequest;
import com.cibertec.service.PaymentStatusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments-status")
@RequiredArgsConstructor
public class PaymentStatusController {
    private final PaymentStatusService paymentStatusService;
    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(paymentStatusService.getAllPaymentStatuses());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(Long id) {
        return ResponseEntity.ok(paymentStatusService.getPaymentStatusById(id));
    }
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid PaymentStatusRequest paymentStatusRequest) {
        return ResponseEntity.ok(paymentStatusService.createPaymentStatus(paymentStatusRequest));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid PaymentStatusRequest paymentStatusRequest) {
        return ResponseEntity.ok(paymentStatusService.updatePaymentStatus(id, paymentStatusRequest));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        paymentStatusService.deletePaymentStatus(id);
        return ResponseEntity.noContent().build();
    }
}
