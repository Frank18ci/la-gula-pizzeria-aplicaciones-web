package com.cibertec.service;

import com.cibertec.dto.DoughTypeRequest;
import com.cibertec.dto.DoughTypeResponse;

import java.util.List;

public interface DoughTypeService {
    List<DoughTypeResponse> getAllDoughTypes();
    DoughTypeResponse getDoughTypeById(Long id);
    DoughTypeResponse createDoughType(DoughTypeRequest doughTypeRequest);
    DoughTypeResponse updateDoughType(Long id, DoughTypeRequest doughTypeRequest);
    void deleteDoughType(Long id);
}
