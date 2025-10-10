package com.cibertec.util;

import com.cibertec.dto.PaymentRequest;
import com.cibertec.dto.PaymentResponse;
import com.cibertec.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {OrderMapper.class,
                PaymentProviderMapper.class,
                PaymentStatusMapper.class })
public interface PaymentMapper {
    @Mapping(source = "orderId", target = "order.id")
    @Mapping(source = "paymentProviderId", target = "paymentProvider.id")
    @Mapping(source = "paymentStatusId", target = "paymentStatus.id")
    Payment toEntity(PaymentRequest paymentRequest);
    PaymentResponse toDto(Payment payment);
    List<PaymentResponse> toDtoList(List<Payment> payments);
}
