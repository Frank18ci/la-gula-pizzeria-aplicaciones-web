package com.cibertec.service;

import java.util.List;

import com.cibertec.dto.DeliveryRequest;
import com.cibertec.dto.DeliveryResponse;

public interface DeliveryService {
      
     List<DeliveryResponse> getAllDeliveries();

     DeliveryResponse getDeliveryById(Long id);

     DeliveryResponse createDelivery(DeliveryRequest deliveryRequest);

     DeliveryResponse updateDelivery(Long id, DeliveryRequest deliveryRequest);
     
    void deleteDelivery(Long id);
}
