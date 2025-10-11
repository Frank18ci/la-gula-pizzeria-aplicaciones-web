package com.cibertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.model.Delivery;

public interface DeliveryRepository extends  JpaRepository<Delivery, Long>{
    
}
