package com.cibertec.service;

import com.cibertec.dto.SizeRequest;
import com.cibertec.dto.SizeResponse;
import com.cibertec.model.Size;
import com.cibertec.repository.SizeRepository;
import com.cibertec.service.impl.SizeServiceImpl;
import com.cibertec.util.SizeMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SizeServiceTest {
    @Mock
    private SizeRepository sizeRepository;

    @Mock
    private SizeMapper sizeMapper;

    @InjectMocks
    private SizeServiceImpl sizeService;

    @Test
    @DisplayName("Obtener todas las tallas - Éxitoso")
    public void testGetAllSizes_Success() {
        List<SizeResponse> sizeResponses = List.of(
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
        List<Size> sizes = List.of(
                Size.builder()
                        .id(1L)
                        .name("Small")
                        .diameterCm(new BigDecimal("20.0"))
                        .priceMultiplier(new BigDecimal("1.00"))
                        .active(true)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build(),
                Size.builder()
                        .id(2L)
                        .name("Medium")
                        .diameterCm(new BigDecimal("30.0"))
                        .priceMultiplier(new BigDecimal("1.25"))
                        .active(true)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()
        );
        when(sizeRepository.findAll()).thenReturn(sizes);
        when(sizeMapper.toDtoList(sizes)).thenReturn(sizeResponses);
        List<SizeResponse> result = sizeService.getSizes();
        assertAll(
                () -> assertEquals(2, result.size()),
                () -> assertEquals("Small", result.getFirst().name()),
                () -> assertEquals("Medium", result.get(1).name())
        );
    }

    @Test
    @DisplayName("Obtener una talla por ID - Éxitoso")
    public void testGetSizeById_Success() {
        Long sizeId = 1L;
        Size size = Size.builder()
                .id(sizeId)
                .name("Large")
                .diameterCm(new BigDecimal("40.0"))
                .priceMultiplier(new BigDecimal("1.50"))
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        SizeResponse sizeResponse = SizeResponse.builder()
                .id(sizeId)
                .name("Large")
                .diameterCm(new BigDecimal("40.0"))
                .priceMultiplier(new BigDecimal("1.50"))
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        when(sizeRepository.findById(sizeId)).thenReturn(java.util.Optional.of(size));
        when(sizeMapper.toDto(size)).thenReturn(sizeResponse);
        SizeResponse result = sizeService.getSizeById(sizeId);
        assertAll(
                () -> assertEquals(sizeId, result.id()),
                () -> assertEquals("Large", result.name()),
                () -> assertEquals(new BigDecimal("40.0"), result.diameterCm()),
                () -> assertEquals(new BigDecimal("1.50"), result.priceMultiplier())
        );
    }

    @Test
    @DisplayName("Crear una nueva talla - Éxitoso")
    public void testCreateSize_Success() {
        SizeRequest sizeRequest = SizeRequest.builder()
                .name("Extra Large")
                .diameterCm(new BigDecimal("50.0"))
                .priceMultiplier(new BigDecimal("1.75"))
                .active(true)
                .build();
        Size sizeToCreate = Size.builder()
                .id(3L)
                .name("Extra Large")
                .diameterCm(new BigDecimal("50.0"))
                .priceMultiplier(new BigDecimal("1.75"))
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        Size savedSize = Size.builder()
                .id(3L)
                .name("Extra Large")
                .diameterCm(new BigDecimal("50.0"))
                .priceMultiplier(new BigDecimal("1.75"))
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        SizeResponse sizeResponse = SizeResponse.builder()
                .id(3L)
                .name("Extra Large")
                .diameterCm(new BigDecimal("50.0"))
                .priceMultiplier(new BigDecimal("1.75"))
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        when(sizeMapper.toEntity(sizeRequest)).thenReturn(sizeToCreate);
        when(sizeRepository.save(sizeToCreate)).thenReturn(savedSize);
        when(sizeMapper.toDto(savedSize)).thenReturn(sizeResponse);
        SizeResponse result = sizeService.createSize(sizeRequest);
        assertAll(
                () -> assertEquals(3L, result.id()),
                () -> assertEquals("Extra Large", result.name()),
                () -> assertEquals(new BigDecimal("50.0"), result.diameterCm()),
                () -> assertEquals(new BigDecimal("1.75"), result.priceMultiplier())
        );
    }

    @Test
    @DisplayName("Actualizar una talla existente - Éxitoso")
    public void testUpdateSize_Success() {
        Long sizeId = 1L;
        SizeRequest sizeRequest = SizeRequest.builder()
                .name("Updated Size")
                .diameterCm(new BigDecimal("35.0"))
                .priceMultiplier(new BigDecimal("1.30"))
                .active(true)
                .build();
        Size existingSize = Size.builder()
                .id(sizeId)
                .name("Old Size")
                .diameterCm(new BigDecimal("30.0"))
                .priceMultiplier(new BigDecimal("1.25"))
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        Size updatedSize = Size.builder()
                .id(sizeId)
                .name("Updated Size")
                .diameterCm(new BigDecimal("35.0"))
                .priceMultiplier(new BigDecimal("1.30"))
                .active(true)
                .createdAt(existingSize.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();
        SizeResponse sizeResponse = SizeResponse.builder()
                .id(sizeId)
                .name("Updated Size")
                .diameterCm(new BigDecimal("35.0"))
                .priceMultiplier(new BigDecimal("1.30"))
                .active(true)
                .createdAt(existingSize.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();
        when(sizeRepository.findById(sizeId)).thenReturn(java.util.Optional.of(existingSize));
        when(sizeRepository.save(updatedSize)).thenReturn(updatedSize);
        when(sizeMapper.toDto(updatedSize)).thenReturn(sizeResponse);
        SizeResponse result = sizeService.updateSize(sizeId, sizeRequest);
        assertAll(
                () -> assertEquals(sizeId, result.id()),
                () -> assertEquals("Updated Size", result.name()),
                () -> assertEquals(new BigDecimal("35.0"), result.diameterCm()),
                () -> assertEquals(new BigDecimal("1.30"), result.priceMultiplier())
        );
    }

    @Test
    @DisplayName("Eliminar una talla por ID - Éxitoso")
    public void testDeleteSize_Success() {
        Long sizeId = 1L;
        Size size = Size.builder()
                .id(sizeId)
                .name("To Be Deleted")
                .diameterCm(new BigDecimal("25.0"))
                .priceMultiplier(new BigDecimal("1.10"))
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        when(sizeRepository.findById(sizeId)).thenReturn(java.util.Optional.of(size));
        sizeService.deleteSize(sizeId);
        verify(sizeRepository).delete(size);
    }
}
