package com.cibertec.service;

import com.cibertec.dto.PaymentRequest;
import com.cibertec.dto.PaymentResponse;

import java.util.List;

public interface PaymentService {
    List<PaymentResponse> getAllPayments();
    PaymentResponse getPaymentById(Long id);
    PaymentResponse createPayment(PaymentRequest paymentRequest);
    PaymentResponse updatePayment(Long id, PaymentRequest paymentRequest);
    void deletePayment(Long id);
}
