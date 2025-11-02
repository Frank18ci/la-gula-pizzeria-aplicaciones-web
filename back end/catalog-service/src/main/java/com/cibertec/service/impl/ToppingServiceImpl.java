package com.cibertec.service.impl;

import com.cibertec.dto.ToppingRequest;
import com.cibertec.dto.ToppingResponse;
import com.cibertec.exception.ResourceNotFound;
import com.cibertec.model.Topping;
import com.cibertec.repository.ToppingRepository;
import com.cibertec.service.ToppingService;
import com.cibertec.storage.ImageStorageService;
import com.cibertec.storage.TypeStorageEnum;
import com.cibertec.util.ToppingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ToppingServiceImpl implements ToppingService {
    private final ToppingRepository toppingRepository;
    private final ToppingMapper toppingMapper;
    private final ImageStorageService imageStorageService;
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
        Topping topping = toppingMapper.toEntity(toppingRequest, toppingRequest.name());

        try{
            imageStorageService.saveImage(toppingRequest.image(), topping.getName(), TypeStorageEnum.TOPPING);
        } catch (Exception e){
            throw new RuntimeException("Could not store the image. Error: " + e.getMessage());
        }

        return toppingMapper.toDto(toppingRepository.save(topping));
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
        toppingFound.setImage(toppingMapper.toEntity(toppingRequest, toppingRequest.name()).getImage());


        try{
            imageStorageService.saveImage(toppingRequest.image(), toppingFound.getName(), TypeStorageEnum.TOPPING);
        } catch (Exception e){
            throw new RuntimeException("Could not store the image. Error: " + e.getMessage());
        }

        return toppingMapper.toDto(toppingRepository.save(toppingFound));
    }

    @Override
    public void deleteTopping(Long id) {
        Topping toppingFound = toppingRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Topping not found with id: " + id)
        );
        toppingRepository.delete(toppingFound);
    }

    @Override
    public List<ToppingResponse> searchToppingsByName(String name) {
        return toppingMapper.toDtoList(toppingRepository.findByNameContaining(name));
    }
}
