package com.cibertec.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cibertec.dto.DeliveryRequest;
import com.cibertec.dto.DeliveryResponse;
import com.cibertec.exception.ResourceNotFound;
import com.cibertec.model.Delivery;
import com.cibertec.repository.DeliveryRepository;
import com.cibertec.service.DeliveryService;
import com.cibertec.util.DeliveryMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryMapper  deliveryMapper;
    
   
    @Override
    public List<DeliveryResponse> getAllDeliveries() {
        return deliveryMapper.toDtoList(deliveryRepository.findAll());
    }

  @Override
    public DeliveryResponse getDeliveryById(Long id) {
        Delivery delivery = deliveryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Delivery not found with id: " + id)
        );
        return deliveryMapper.toDto(delivery);
    }

   
    @Override
    public DeliveryResponse createDelivery(DeliveryRequest deliveryRequest) {
        Delivery delivery = deliveryMapper.toEntity(deliveryRequest);
        delivery.setCreatedAt(LocalDateTime.now());
        delivery.setUpdatedAt(LocalDateTime.now());
        return deliveryMapper.toDto(deliveryRepository.save(delivery));
    }

    @Override
    public DeliveryResponse updateDelivery(Long id, DeliveryRequest deliveryRequest) {
        Delivery deliveryFound = deliveryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Delivery not found with id: " + id)
        );

        deliveryFound.setOrderId(deliveryRequest.orderId());
        deliveryFound.setAddressId(deliveryRequest.addressId());
        deliveryFound.setMethod(deliveryRequest.method());
        deliveryFound.setStatus(deliveryRequest.status());
        deliveryFound.setDriverName(deliveryRequest.driverName());
        deliveryFound.setDriverPhone(deliveryRequest.driverPhone());
        deliveryFound.setInstructions(deliveryRequest.instructions());
        deliveryFound.setUpdatedAt(LocalDateTime.now());

        return deliveryMapper.toDto(deliveryRepository.save(deliveryFound));
    }

     @Override
    public void deleteDelivery(Long id) {
        Delivery deliveryFound = deliveryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Delivery not found with id: " + id)
        );
        deliveryRepository.delete(deliveryFound);
    }
    
}
