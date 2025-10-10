package com.cibertec.repository;


import com.cibertec.model.PaymentProvider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentProviderRepository extends JpaRepository<PaymentProvider, Long> {
}
