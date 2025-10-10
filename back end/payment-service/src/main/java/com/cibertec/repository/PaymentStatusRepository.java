package com.cibertec.repository;

import com.cibertec.model.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentStatusRepository  extends JpaRepository<PaymentStatus, Long> {
}
