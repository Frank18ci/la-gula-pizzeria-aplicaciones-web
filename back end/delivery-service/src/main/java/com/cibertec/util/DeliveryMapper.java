package com.cibertec.util;

import java.util.List;

import org.mapstruct.Mapper;

import com.cibertec.dto.DeliveryRequest;
import com.cibertec.dto.DeliveryResponse;
import com.cibertec.model.Delivery;

@Mapper(componentModel = "spring")
public interface DeliveryMapper {

    Delivery toEntity(DeliveryRequest deliveryRequest);

    DeliveryResponse toDto(Delivery delivery);

    List<DeliveryResponse> toDtoList(List<Delivery> deliveries);
}
