package com.cibertec.util;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cibertec.dto.DeliveryRequest;
import com.cibertec.dto.DeliveryResponse;
import com.cibertec.model.Delivery;

@Mapper(componentModel = "spring")
public interface DeliveryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "eta", ignore = true)
    @Mapping(target = "deliveredAt", ignore = true)
    Delivery toEntity(DeliveryRequest deliveryRequest);

    DeliveryResponse toDto(Delivery delivery);

    List<DeliveryResponse> toDtoList(List<Delivery> deliveries);
}
