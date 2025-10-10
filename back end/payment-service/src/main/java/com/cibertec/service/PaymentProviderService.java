package com.cibertec.service;

import com.cibertec.dto.PaymentProviderRequest;
import com.cibertec.dto.PaymentProviderResponse;

import java.util.List;

public interface PaymentProviderService {
    List<PaymentProviderResponse> getAllPaymentProviders();
    PaymentProviderResponse getPaymentProviderById(Long id);
    PaymentProviderResponse createPaymentProvider(PaymentProviderRequest paymentProviderRequest);
    PaymentProviderResponse updatePaymentProvider(Long id, PaymentProviderRequest paymentProviderRequest);
    void deletePaymentProvider(Long id);
}
