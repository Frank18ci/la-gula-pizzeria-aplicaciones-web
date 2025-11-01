package com.cibertec.service.impl;

import com.cibertec.client.OrdeningClient;
import com.cibertec.dto.PaymentRequest;
import com.cibertec.dto.PaymentResponse;
import com.cibertec.model.Payment;
import com.cibertec.repository.PaymentRepository;
import com.cibertec.service.PaymentService;
import com.cibertec.util.PaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentMapper paymentMapper;
    private final PaymentRepository paymentRepository;

    private final OrdeningClient ordeningClient;

    @Override
    public List<PaymentResponse> getAllPayments() {
        return paymentMapper.toDtoList(paymentRepository.findAll());
    }

    @Override
    public PaymentResponse getPaymentById(Long id) {
        return paymentMapper.toDto( paymentRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Payment not found with id: " + id)
        ));
    }

    @Override
    public PaymentResponse createPayment(PaymentRequest paymentRequest) {
        ordeningClient.findById(paymentRequest.orderId());
        return paymentMapper.toDto(paymentRepository.save(paymentMapper.toEntity(paymentRequest)));
    }

    @Override
    public PaymentResponse updatePayment(Long id, PaymentRequest paymentRequest) {
        Payment paymentFound = paymentRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Payment not found with id: " + id)
        );

        ordeningClient.findById(paymentRequest.orderId());

        paymentFound.setOrder(paymentMapper.toEntity(paymentRequest).getOrder());
        paymentFound.setAmount(paymentRequest.amount());
        paymentFound.setCurrency(paymentRequest.currency());
        paymentFound.setPaymentProvider(paymentMapper.toEntity(paymentRequest).getPaymentProvider());
        paymentFound.setPaymentStatus(paymentMapper.toEntity(paymentRequest).getPaymentStatus());
        paymentFound.setExternalId(paymentRequest.externalId());
        return paymentMapper.toDto(paymentRepository.save(paymentFound));
    }

    @Override
    public void deletePayment(Long id) {
        Payment paymentFound = paymentRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Payment not found with id: " + id)
        );
        paymentRepository.delete(paymentFound);
    }
}
