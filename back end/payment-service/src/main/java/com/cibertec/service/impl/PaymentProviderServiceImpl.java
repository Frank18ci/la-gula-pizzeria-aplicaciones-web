package com.cibertec.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cibertec.dto.PaymentProviderRequest;
import com.cibertec.dto.PaymentProviderResponse;
import com.cibertec.model.PaymentProvider;
import com.cibertec.repository.PaymentProviderRepository;
import com.cibertec.service.PaymentProviderService;
import com.cibertec.util.PaymentProviderMapper;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class PaymentProviderServiceImpl implements PaymentProviderService {
    
    private final PaymentProviderMapper paymentProviderMapper;

    private final PaymentProviderRepository paymentProviderRepository;

    
    @Override
    public List<PaymentProviderResponse> getAllPaymentProviders() {
        return paymentProviderMapper.toDtoList(paymentProviderRepository.findAll());
    }

    @Override
    public PaymentProviderResponse getPaymentProviderById(Long id) {
        return paymentProviderMapper.toDto(paymentProviderRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Payment Provider not found with id: " + id)));
    }

    @Override
    public PaymentProviderResponse createPaymentProvider(PaymentProviderRequest paymentProviderRequest) {
        return paymentProviderMapper.toDto(paymentProviderRepository.save(
                paymentProviderMapper.toEntity(paymentProviderRequest)
        ));
    }

    @Override
    public PaymentProviderResponse updatePaymentProvider(Long id, PaymentProviderRequest paymentProviderRequest) {
        PaymentProvider paymentProviderFound = paymentProviderRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Payment Provider not found with id: " + id));
        paymentProviderFound.setName(paymentProviderRequest.name());
        return paymentProviderMapper.toDto(paymentProviderRepository.save(paymentProviderFound));
    }

    @Override
    public void deletePaymentProvider(Long id) {
        PaymentProvider paymentProviderFound = paymentProviderRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Payment Provider not found with id: " + id));
        paymentProviderRepository.delete(paymentProviderFound);
    }
}
