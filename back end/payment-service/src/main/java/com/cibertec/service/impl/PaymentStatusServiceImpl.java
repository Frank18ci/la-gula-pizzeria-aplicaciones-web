package com.cibertec.service.impl;

import com.cibertec.dto.PaymentStatusRequest;
import com.cibertec.dto.PaymentStatusResponse;
import com.cibertec.model.PaymentStatus;
import com.cibertec.repository.PaymentStatusRepository;
import com.cibertec.service.PaymentStatusService;
import com.cibertec.util.PaymentStatusMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentStatusServiceImpl  implements PaymentStatusService {
    private final PaymentStatusRepository paymentsStatusRepository;
    private final PaymentStatusMapper paymentStatusMapper;
    @Override
    public List<PaymentStatusResponse> getAllPaymentStatuses() {
        return  paymentStatusMapper.toDtoList(paymentsStatusRepository.findAll());
    }

    @Override
    public PaymentStatusResponse getPaymentStatusById(Long id) {
        return paymentStatusMapper.toDto( paymentsStatusRepository.findById(id).orElseThrow(
                () -> new RuntimeException("PaymentStatus not found with id: " + id)
        ));
    }

    @Override
    public PaymentStatusResponse createPaymentStatus(PaymentStatusRequest paymentStatusRequest) {
        return paymentStatusMapper.toDto(paymentsStatusRepository.save(paymentStatusMapper.toEntity(paymentStatusRequest)));
    }

    @Override
    public PaymentStatusResponse updatePaymentStatus(Long id, PaymentStatusRequest paymentStatusRequest) {
        PaymentStatus paymentStatusFound = paymentsStatusRepository.findById(id).orElseThrow(
                () -> new RuntimeException("PaymentStatus not found with id: " + id)
        );
        paymentStatusFound.setName(paymentStatusRequest.name());
        return paymentStatusMapper.toDto(paymentsStatusRepository.save(paymentStatusFound));
    }

    @Override
    public void deletePaymentStatus(Long id) {
        PaymentStatus paymentStatusFound = paymentsStatusRepository.findById(id).orElseThrow(
                () -> new RuntimeException("PaymentStatus not found with id: " + id)
        );
        paymentsStatusRepository.delete(paymentStatusFound);

    }
}
