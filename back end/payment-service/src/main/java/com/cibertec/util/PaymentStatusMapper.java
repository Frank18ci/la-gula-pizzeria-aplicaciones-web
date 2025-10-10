package com.cibertec.util;

import com.cibertec.dto.PaymentStatusRequest;
import com.cibertec.dto.PaymentStatusResponse;
import com.cibertec.model.PaymentStatus;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentStatusMapper {
    PaymentStatus toEntity(PaymentStatusRequest paymentStatusRequest);
    PaymentStatusResponse toDto(PaymentStatus paymentStatus);
    List<PaymentStatusResponse> toDtoList(List<PaymentStatus> paymentStatuses);
}
