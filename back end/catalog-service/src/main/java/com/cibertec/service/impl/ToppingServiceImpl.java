package com.cibertec.service.impl;

import com.cibertec.dto.ToppingRequest;
import com.cibertec.dto.ToppingResponse;
import com.cibertec.exception.ResourceNotFound;
import com.cibertec.model.Topping;
import com.cibertec.repository.ToppingRepository;
import com.cibertec.service.ToppingService;
import com.cibertec.util.ToppingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ToppingServiceImpl implements ToppingService {
    private final ToppingRepository toppingRepository;
    private final ToppingMapper toppingMapper;
    @Override
    public List<ToppingResponse> getToppings() {
        return toppingMapper.toDtoList(toppingRepository.findAll());
    }

    @Override
    public ToppingResponse getToppingById(Long id) {
        return toppingMapper.toDto(toppingRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Topping not found with id: " + id)
        ));
    }

    @Override
    public ToppingResponse createTopping(ToppingRequest toppingRequest) {
        return toppingMapper.toDto(toppingRepository.save(toppingMapper.toEntity(toppingRequest)));
    }

    @Override
    public ToppingResponse updateTopping(Long id, ToppingRequest toppingRequest) {
        Topping toppingFound = toppingRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Topping not found with id: " + id)
        );
        toppingFound.setName(toppingRequest.name());
        toppingFound.setCategory(toppingRequest.category());
        toppingFound.setIsVegetarian(toppingRequest.isVegetarian());
        toppingFound.setBasePrice(toppingRequest.basePrice());
        toppingFound.setActive(toppingRequest.active());
        return toppingMapper.toDto(toppingRepository.save(toppingFound));
    }

    @Override
    public void deleteTopping(Long id) {
        Topping toppingFound = toppingRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Topping not found with id: " + id)
        );
        toppingRepository.delete(toppingFound);
    }
}
