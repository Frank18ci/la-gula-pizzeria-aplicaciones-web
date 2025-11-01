package com.cibertec.controller;

import com.cibertec.dto.SizeResponse;
import com.cibertec.exception.ExceptionHandleController;
import com.cibertec.service.SizeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class SizeControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private SizeController sizeController;

    @Mock
    private SizeService sizeService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(sizeController)
                .setControllerAdvice(new ExceptionHandleController())
                .build();
    }

    @Test
    @DisplayName("Obtener todos los tallas - Éxito")
    void testGetAllSizes_Success() throws Exception {
        List<SizeResponse> sizes = List.of(
                SizeResponse.builder()
                        .id(1L)
                        .name("Small")
                        .diameterCm(new BigDecimal("20.0"))
                        .priceMultiplier(new BigDecimal("1.00"))
                        .active(true)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build(),
                SizeResponse.builder()
                        .id(2L)
                        .name("Medium")
                        .diameterCm(new BigDecimal("30.0"))
                        .priceMultiplier(new BigDecimal("1.25"))
                        .active(true)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()
        );

        when(sizeService.getSizes()).thenReturn(sizes);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/sizes")
                                .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Small"))
                .andExpect(jsonPath("$[0].diameterCm").value(20.0))
                .andExpect(jsonPath("$[0].priceMultiplier").value(1.00))
                .andExpect(jsonPath("$[0].active").value(true))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Medium"))
                .andExpect(jsonPath("$[1].diameterCm").value(30.0))
                .andExpect(jsonPath("$[1].priceMultiplier").value(1.25))
                .andExpect(jsonPath("$[1].active").value(true));
    }

    @Test
    @DisplayName("Obtener una talla - Éxito")
    void testGetSizeById_Success() throws Exception {
        SizeResponse sizeResponse = SizeResponse.builder()
                .id(1L)
                .name("Small")
                .diameterCm(new BigDecimal("20.0"))
                .priceMultiplier(new BigDecimal("1.00"))
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        when(sizeService.getSizeById(1L)).thenReturn(sizeResponse);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/sizes/{id}", 1L)
                                .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Small"))
                .andExpect(jsonPath("$.diameterCm").value(20.0))
                .andExpect(jsonPath("$.priceMultiplier").value(1.00))
                .andExpect(jsonPath("$.active").value(true)
                );
    }

    @Test
    @DisplayName("Crear una talla - Éxito")
    void testCreateSize_Success() throws Exception {
        SizeResponse sizeResponse = SizeResponse.builder()
                .id(1L)
                .name("Small")
                .diameterCm(new BigDecimal("20.0"))
                .priceMultiplier(new BigDecimal("1.00"))
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        when(sizeService.createSize(ArgumentMatchers.any())).thenReturn(sizeResponse);
        String sizeJson = """
                {
                    "name": "Small",
                    "diameterCm": 20.0,
                    "priceMultiplier": 1.00,
                    "active": true
                }
                """;
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/sizes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(sizeJson)
                                .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Small"))
                .andExpect(jsonPath("$.diameterCm").value(20.0))
                .andExpect(jsonPath("$.priceMultiplier").value(1.00))
                .andExpect(jsonPath("$.active").value(true));
    }

    @Test
    @DisplayName("Actualizar una talla - Éxito")
    void testUpdateSize_Success() throws Exception {
        SizeResponse sizeResponse = SizeResponse.builder()
                .id(1L)
                .name("Small")
                .diameterCm(new BigDecimal("20.0"))
                .priceMultiplier(new BigDecimal("1.00"))
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        when(sizeService.updateSize(ArgumentMatchers.eq(1L), ArgumentMatchers.any())).thenReturn(sizeResponse);
        String sizeJson = """
                {
                    "name": "Small",
                    "diameterCm": 20.0,
                    "priceMultiplier": 1.00,
                    "active": true
                }
                """;
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/sizes/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(sizeJson)
                                .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Small"))
                .andExpect(jsonPath("$.diameterCm").value(20.0))
                .andExpect(jsonPath("$.priceMultiplier").value(1.00))
                .andExpect(jsonPath("$.active").value(true));
    }

    @Test
    @DisplayName("Eliminar una talla - Éxito")
    void testDeleteSize_Success() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/sizes/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }
}
