package com.cibertec.controller;

import com.cibertec.dto.PaymentProviderRequest;
import com.cibertec.service.PaymentProviderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments-providers")
@RequiredArgsConstructor
public class PaymentProviderController {
    private final PaymentProviderService paymentProviderService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(paymentProviderService.getAllPaymentProviders());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(Long id) {
        return ResponseEntity.ok(paymentProviderService.getPaymentProviderById(id));
    }
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid PaymentProviderRequest paymentProviderRequest) {
        return ResponseEntity.ok(paymentProviderService.createPaymentProvider(paymentProviderRequest));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid PaymentProviderRequest paymentProviderRequest) {
        return ResponseEntity.ok(paymentProviderService.updatePaymentProvider(id, paymentProviderRequest));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        paymentProviderService.deletePaymentProvider(id);
        return ResponseEntity.noContent().build();
    }


}
