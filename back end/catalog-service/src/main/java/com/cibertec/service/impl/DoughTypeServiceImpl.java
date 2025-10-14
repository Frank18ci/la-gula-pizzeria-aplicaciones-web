package com.cibertec.service.impl;

import com.cibertec.dto.DoughTypeRequest;
import com.cibertec.dto.DoughTypeResponse;
import com.cibertec.exception.ResourceNotFound;
import com.cibertec.model.DoughType;
import com.cibertec.repository.DoughTypeRepository;
import com.cibertec.service.DoughTypeService;
import com.cibertec.util.DoughTypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoughTypeServiceImpl implements DoughTypeService {
    private final DoughTypeRepository doughTypeRepository;
    private final DoughTypeMapper doughTypeMapper;
    @Override
    public List<DoughTypeResponse> getAllDoughTypes() {
        return doughTypeMapper.toDtoList(doughTypeRepository.findAll());
    }

    @Override
    public DoughTypeResponse getDoughTypeById(Long id) {
        return doughTypeMapper.toDto(doughTypeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("DoughType not found with id: " + id)
        ));
    }

    @Override
    public DoughTypeResponse createDoughType(DoughTypeRequest doughTypeRequest) {
        return doughTypeMapper.toDto(doughTypeRepository.save(doughTypeMapper.toEntity(doughTypeRequest)));
    }

    @Override
    public DoughTypeResponse updateDoughType(Long id, DoughTypeRequest doughTypeRequest) {
        DoughType doughTypeFound = doughTypeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("DoughType not found with id: " + id)
        );
        doughTypeFound.setName(doughTypeRequest.name());
        doughTypeFound.setIsGlutenFree(doughTypeRequest.isGlutenFree());
        doughTypeFound.setExtraPrice(doughTypeRequest.extraPrice());
        doughTypeFound.setActive(doughTypeRequest.active());
        return doughTypeMapper.toDto(doughTypeRepository.save(doughTypeFound));
    }

    @Override
    public void deleteDoughType(Long id) {
        DoughType doughTypeFound = doughTypeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("DoughType not found with id: " + id)
        );
        doughTypeRepository.delete(doughTypeFound);
    }
}
