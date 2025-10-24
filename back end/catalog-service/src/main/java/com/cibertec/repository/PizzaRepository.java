package com.cibertec.repository;

import com.cibertec.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PizzaRepository extends JpaRepository<Pizza, Long> {
    List<Pizza> findByBasePriceBetweenAnd(Double minPrice, Double maxPrice, Long sizeId);
}
