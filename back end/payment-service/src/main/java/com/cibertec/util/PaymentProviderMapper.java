package com.cibertec.util;

import com.cibertec.dto.PaymentProviderRequest;
import com.cibertec.dto.PaymentProviderResponse;
import com.cibertec.model.PaymentProvider;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentProviderMapper {
    PaymentProvider toEntity(PaymentProviderRequest paymentProviderRequest);
    PaymentProviderResponse toDto(PaymentProvider paymentProvider);
    List<PaymentProviderResponse> toDtoList(List<PaymentProvider> paymentProviders);
}
