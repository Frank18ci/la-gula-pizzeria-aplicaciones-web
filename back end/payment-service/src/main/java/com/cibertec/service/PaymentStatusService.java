package com.cibertec.service;

import com.cibertec.dto.PaymentStatusRequest;
import com.cibertec.dto.PaymentStatusResponse;

import java.util.List;

public interface PaymentStatusService {
    List<PaymentStatusResponse> getAllPaymentStatuses();
    PaymentStatusResponse getPaymentStatusById(Long id);
    PaymentStatusResponse createPaymentStatus(PaymentStatusRequest paymentStatusRequest);
    PaymentStatusResponse updatePaymentStatus(Long id, PaymentStatusRequest paymentStatusRequest);
    void deletePaymentStatus(Long id);
}
