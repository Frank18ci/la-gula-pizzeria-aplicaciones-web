package com.cibertec.controller;

import com.cibertec.dto.AddressRequest;
import com.cibertec.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/addresses")
public class AddressController {
    private final AddressService addressService;
    @GetMapping
    public ResponseEntity<?> getAddresses() {
        return ResponseEntity.ok(addressService.getAllAddresses());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getAddressById(@PathVariable Long id) {
        return ResponseEntity.ok(addressService.getAddressById(id));
    }
    @PostMapping
    public ResponseEntity<?> createAddress(@RequestBody @Valid AddressRequest addressRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.createAddress(addressRequest));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAddress(@PathVariable Long id, @RequestBody @Valid AddressRequest addressRequest) {
        return ResponseEntity.ok(addressService.updateAddress(id, addressRequest));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }
}
