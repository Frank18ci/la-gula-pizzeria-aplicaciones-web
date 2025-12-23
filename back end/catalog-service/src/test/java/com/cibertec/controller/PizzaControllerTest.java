package com.cibertec.controller;

import com.cibertec.dto.PizzaRequest;
import com.cibertec.dto.PizzaResponse;
import com.cibertec.service.PizzaService;
import com.cibertec.storage.ImageStorageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PizzaController.class)
public class PizzaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PizzaService pizzaService;

    @MockitoBean
    private ImageStorageService imageStorageService;

    @Test
    void getAllShouldReturnList() throws Exception {

        PizzaResponse response = PizzaResponse.builder()
                .id(1L)
                .name("Margarita")
                .build();

        when(pizzaService.getPizzas()).thenReturn(List.of(response));

        mockMvc.perform(get("/pizzas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Margarita")); // ✔ CORREGIDO

        verify(pizzaService).getPizzas();
    }

     @Test
     void getByIdShouldReturnPizza() throws Exception{
            PizzaResponse response = PizzaResponse.builder()
                    .id(1L)
                    .name("Margarita")
                    .build();
            when(pizzaService.getPizzaById(1L)).thenReturn(response);

            mockMvc.perform(get("/pizzas/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("Margarita"));
            verify(pizzaService).getPizzaById(1L);
     }

    @Test
    void createdPizzaShouldReturnCreated() throws Exception {
        // Test implementation goes here
        MockMultipartFile image = new MockMultipartFile(
                "image",
                "pizza.jpg",
                "image/jpeg",
                "fakeimage".getBytes()
        );

        when(pizzaService.createPizza(any(PizzaRequest.class))).thenReturn(
                PizzaResponse.builder()
                        .id(1L)
                        .name("Margarita")
                        .build()
        );
        mockMvc.perform(multipart("/pizzas")
                        .file(image)
                        .param("name", "Margarita")
                        .param("description", "Delicious pizza")
                        .param("basePrice", "25")
                        .param("active", "true")
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
        verify(pizzaService).createPizza(any(PizzaRequest.class));
    }

    @Test
    void updatePizzaShouldReturnOk() throws Exception {

        MockMultipartFile image = new MockMultipartFile(
                "image", "pizza.jpg",
                "image/jpeg", "fakeimage".getBytes()
        );

        when(pizzaService.updatePizza(eq(1L), any(PizzaRequest.class))).thenReturn(
                PizzaResponse.builder()
                        .id(1L)
                        .name("Margarita Updated")
                        .build()
        );

        mockMvc.perform(multipart("/pizzas/1")
                        .file(image)
                        .param("name", "Actualizada")
                        .param("description", "desc")
                        .param("basePrice", "30")
                        .param("active", "true")
                        .with(request -> {
                            request.setMethod("PUT"); return request; })
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name")
                        .value("Margarita Updated"));

        verify(pizzaService).updatePizza(eq(1L), any(PizzaRequest.class));
    }


    @Test
    void deletePizzaShouldReturnNoContent() throws Exception {
        doNothing().when(pizzaService).deletePizza(1L);

        mockMvc.perform(delete("/pizzas/1"))
                .andExpect(status().isNoContent());

        verify(pizzaService).deletePizza(1L);
    }

    @Test
    void searchOptionsShouldReturnList() throws Exception {

        PizzaResponse pizza = PizzaResponse.builder()
                .id(1L)
                .name("Filtrada")
                .build();

        when(pizzaService.getAllPizzasByPriceBetweenAndSizeIdAndDoughTypeId(
                any(), any(), anyLong(), anyLong()))
                .thenReturn(List.of(pizza));

        mockMvc.perform(get("/pizzas/searchOptions")
                        .param("minPrice", "10")
                        .param("maxPrice", "50")
                        .param("sizeId", "1")
                        .param("toppingId", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Filtrada"));

        verify(pizzaService).getAllPizzasByPriceBetweenAndSizeIdAndDoughTypeId(
                any(), any(), anyLong(), anyLong());
    }


    @Test
    void searchByNameShouldReturnList() throws Exception {

        when(pizzaService.getAllPizzasByName("Pep"))
                .thenReturn(List.of(
                        PizzaResponse.builder().id(1L).name("Pepperoni").build()
                ));

        mockMvc.perform(get("/pizzas/search")
                        .param("name", "Pep"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Pepperoni"));

        verify(pizzaService).getAllPizzasByName("Pep");
    }

    @Test
    void getImageShouldReturnResponseEntityFromStorage() throws Exception {

        when(imageStorageService.obtainImage(eq("foto.jpg"), any()))
                .thenReturn(org.springframework.http.ResponseEntity.ok().build());

        mockMvc.perform(get("/pizzas/imagen/foto.jpg"))
                .andExpect(status().isOk());

        verify(imageStorageService).obtainImage(eq("foto.jpg"), any());
    }


}
