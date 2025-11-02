package com.cibertec.service;

import com.cibertec.dto.ToppingRequest;
import com.cibertec.dto.ToppingResponse;

import java.util.List;

public interface ToppingService {
    List<ToppingResponse> getToppings();

    ToppingResponse getToppingById(Long id);

    ToppingResponse createTopping(ToppingRequest toppingRequest);

    ToppingResponse updateTopping(Long id, ToppingRequest toppingRequest);

    void deleteTopping(Long id);

    List<ToppingResponse> searchToppingsByName(String name);
}
