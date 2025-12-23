package com.cibertec.service;

import com.cibertec.dto.PizzaRequest;
import com.cibertec.dto.PizzaResponse;
import com.cibertec.exception.ResourceNotFound;
import com.cibertec.model.Pizza;
import com.cibertec.repository.PizzaRepository;
import com.cibertec.repository.SizeRepository;
import com.cibertec.repository.ToppingRepository;
import com.cibertec.service.impl.PizzaServiceImpl;
import com.cibertec.storage.ImageStorageService;
import com.cibertec.storage.TypeStorageEnum;
import com.cibertec.util.PizzaMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PizzaServiceTest {

    @Mock
    private PizzaRepository pizzaRepository;

    @Mock
    private ToppingRepository toppingRepository;

    @Mock
    private SizeRepository sizeRepository;

    @Mock
    private PizzaMapper pizzaMapper;

    @Mock
    private ImageStorageService imageStorageService;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private PizzaServiceImpl pizzaService;

    // ---------------- GET ALL ----------------

    @Test
    void getPizzasShouldReturnList() {
        Pizza pizza = new Pizza();
        PizzaResponse response = PizzaResponse.builder()
                .id(1L)
                .name("Margarita")
                .build();

        when(pizzaRepository.findAll()).thenReturn(List.of(pizza));
        when(pizzaMapper.toDtoList(List.of(pizza))).thenReturn(List.of(response));

        List<PizzaResponse> result = pizzaService.getPizzas();

        assertEquals(1, result.size());
        verify(pizzaRepository).findAll();
    }

    // ---------------- GET BY ID ----------------

    @Test
    void getPizzaByIdShouldReturnPizza() {
        Pizza pizza = new Pizza();
        PizzaResponse response = PizzaResponse.builder()
                .id(1L)
                .name("Pepperoni")
                .build();

        when(pizzaRepository.findById(1L)).thenReturn(Optional.of(pizza));
        when(pizzaMapper.toDto(pizza)).thenReturn(response);

        PizzaResponse result = pizzaService.getPizzaById(1L);

        assertEquals("Pepperoni", result.name());
    }

    @Test
    void getPizzaByIdNotFoundShouldThrowException() {
        when(pizzaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFound.class,
                () -> pizzaService.getPizzaById(1L));
    }

    // ---------------- CREATE ----------------

    @Test
    void createPizzaShouldSaveAndReturnPizza() throws Exception {

        PizzaRequest request = PizzaRequest.builder()
                .name("Margarita")
                .basePrice(BigDecimal.valueOf(20))
                .active(true)
                .image(multipartFile)
                .build();

        Pizza pizza = new Pizza();
        pizza.setName("Margarita");

        PizzaResponse response = PizzaResponse.builder()
                .id(1L)
                .name("Margarita")
                .build();

        when(pizzaMapper.toEntity(
                any(PizzaRequest.class),
                any(ToppingRepository.class),
                any(SizeRepository.class),
                anyString()
        )).thenReturn(pizza);

        doNothing().when(imageStorageService)
                .saveImage(any(MultipartFile.class), anyString(), eq(TypeStorageEnum.PIZZA));

        when(pizzaRepository.save(pizza)).thenReturn(pizza);
        when(pizzaMapper.toDto(pizza)).thenReturn(response);

        PizzaResponse result = pizzaService.createPizza(request);

        assertEquals("Margarita", result.name());
        verify(pizzaRepository).save(pizza);
    }

    // ---------------- UPDATE ----------------

    @Test
    void updatePizzaShouldUpdateAndReturnPizza() throws Exception {

        PizzaRequest request = PizzaRequest.builder()
                .name("Actualizada")
                .description("Nueva descripcion")
                .basePrice(BigDecimal.valueOf(30))
                .active(true)
                .image(multipartFile)
                .build();

        Pizza pizzaFound = new Pizza();
        pizzaFound.setToppings(new ArrayList<>());
        pizzaFound.setSizes(new ArrayList<>());

        Pizza pizzaMapped = new Pizza();
        pizzaMapped.setImage("imagen.jpg");

        PizzaResponse response = PizzaResponse.builder()
                .id(1L)
                .name("Actualizada")
                .build();

        when(pizzaRepository.findById(1L)).thenReturn(Optional.of(pizzaFound));

        when(pizzaMapper.toEntity(
                any(PizzaRequest.class),
                any(ToppingRepository.class),
                any(SizeRepository.class),
                anyString()
        )).thenReturn(pizzaMapped);

        doNothing().when(imageStorageService)
                .saveImage(any(MultipartFile.class), anyString(), eq(TypeStorageEnum.PIZZA));

        when(pizzaRepository.save(pizzaFound)).thenReturn(pizzaFound);
        when(pizzaMapper.toDto(pizzaFound)).thenReturn(response);

        PizzaResponse result = pizzaService.updatePizza(1L, request);

        assertEquals("Actualizada", result.name());
    }

    // ---------------- DELETE ----------------

    @Test
    void deletePizzaShouldDelete() {
        Pizza pizza = new Pizza();

        when(pizzaRepository.findById(1L)).thenReturn(Optional.of(pizza));

        pizzaService.deletePizza(1L);

        verify(pizzaRepository).delete(pizza);
    }

    @Test
    void deletePizzaNotFoundShouldThrowException() {
        when(pizzaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFound.class,
                () -> pizzaService.deletePizza(1L));
    }
}
