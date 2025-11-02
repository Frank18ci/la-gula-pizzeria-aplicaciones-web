package com.cibertec.service.impl;

import com.cibertec.dto.PizzaRequest;
import com.cibertec.dto.PizzaResponse;
import com.cibertec.exception.ResourceNotFound;
import com.cibertec.model.Pizza;
import com.cibertec.repository.PizzaRepository;
import com.cibertec.repository.SizeRepository;
import com.cibertec.repository.ToppingRepository;
import com.cibertec.service.PizzaService;
import com.cibertec.storage.ImageStorageService;
import com.cibertec.storage.TypeStorageEnum;
import com.cibertec.util.PizzaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PizzaServiceImpl implements PizzaService {
    private final PizzaRepository pizzaRepository;
    private final ToppingRepository toppingRepository;
    private final SizeRepository sizeRepository;
    private final PizzaMapper pizzaMapper;
    private final ImageStorageService imageStorageService;

    /**
     * Get all pizzas.
     * @return List of PizzaResponse
     */
    @Override
    public List<PizzaResponse> getPizzas() {
        return pizzaMapper.toDtoList(pizzaRepository.findAll());
    }

    @Override
    public PizzaResponse getPizzaById(Long id) {
        return pizzaMapper.toDto(pizzaRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Pizza not found with id: " + id)
        ));
    }

    @Override
    public PizzaResponse createPizza(PizzaRequest pizzaRequest) {
        Pizza pizza = pizzaMapper.toEntity(pizzaRequest, toppingRepository, sizeRepository, pizzaRequest.name());
        try{
            imageStorageService.saveImage(pizzaRequest.image(), pizza.getName(), TypeStorageEnum.PIZZA);
        } catch (Exception e){
            throw new RuntimeException("Could not store the image. Error: " + e.getMessage());
        }
        return pizzaMapper.toDto(pizzaRepository.save(pizza));
    }

    @Override
    public PizzaResponse updatePizza(Long id, PizzaRequest pizzaRequest) {
        Pizza pizzaFound = pizzaRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Pizza not found with id: " + id)
        );
        pizzaFound.setName(pizzaRequest.name());
        pizzaFound.setDescription(pizzaRequest.description());
        pizzaFound.setBasePrice(pizzaRequest.basePrice());
        pizzaFound.setImage(pizzaMapper.toEntity(pizzaRequest, toppingRepository, sizeRepository, pizzaRequest.name()).getImage());
        pizzaFound.setActive(pizzaRequest.active());

        if(pizzaRequest.toppingIds() != null) {
            pizzaFound.setToppings(pizzaMapper.map(pizzaRequest.toppingIds(), toppingRepository));
        } else {
            pizzaFound.getToppings().clear();
        }

        try{
            imageStorageService.saveImage(pizzaRequest.image(), pizzaFound.getName(), TypeStorageEnum.PIZZA);
        } catch (Exception e){
            throw new RuntimeException("Could not store the image. Error: " + e.getMessage());
        }

        return pizzaMapper.toDto(pizzaRepository.save(pizzaFound));
    }

    @Override
    public void deletePizza(Long id) {
        Pizza pizzaFound = pizzaRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Pizza not found with id: " + id)
        );
        pizzaRepository.delete(pizzaFound);
    }

    @Override
    public List<PizzaResponse> getAllPizzasByPriceBetweenAndSizeIdAndDoughTypeId(BigDecimal minPrice, BigDecimal maxPrice, Long sizeId, Long toppingId) {
        if(sizeId == 0 && toppingId == 0) {
            return pizzaMapper.toDtoList(pizzaRepository.findByBasePriceBetween(minPrice, maxPrice));
        } else if(sizeId != 0 && toppingId == 0) {
            return pizzaMapper.toDtoList(pizzaRepository.findByPriceRangeAndSizeId(minPrice, maxPrice, sizeId));
        } else if(sizeId == 0) {
            return pizzaMapper.toDtoList(pizzaRepository.findByPriceRangeAndToppingId(minPrice, maxPrice, toppingId));
        }

        return pizzaMapper.toDtoList(pizzaRepository.findByPriceRangeAndSizeIdAndToppingId(minPrice, maxPrice, sizeId, toppingId));
    }

    @Override
    public List<PizzaResponse> getAllPizzasByName(String name) {
        return pizzaMapper.toDtoList(pizzaRepository.findByNameContaining(name));
    }
}
