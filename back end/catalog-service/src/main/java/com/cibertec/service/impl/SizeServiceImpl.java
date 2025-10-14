package com.cibertec.service.impl;

import com.cibertec.dto.SizeRequest;
import com.cibertec.dto.SizeResponse;
import com.cibertec.exception.ResourceNotFound;
import com.cibertec.model.Size;
import com.cibertec.repository.SizeRepository;
import com.cibertec.service.SizeService;
import com.cibertec.util.SizeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SizeServiceImpl implements SizeService {
    private final SizeRepository sizeRepository;
    private final SizeMapper sizeMapper;
    @Override
    public List<SizeResponse> getSizes() {
        return sizeMapper.toDtoList(sizeRepository.findAll());
    }

    @Override
    public SizeResponse getSizeById(Long id) {
        return sizeMapper.toDto(sizeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Size not found with id: " + id)
        ));
    }

    @Override
    public SizeResponse createSize(SizeRequest sizeRequest) {
        return sizeMapper.toDto(sizeRepository.save(sizeMapper.toEntity(sizeRequest)));
    }

    @Override
    public SizeResponse updateSize(Long id, SizeRequest sizeRequest) {
        Size sizeFound = sizeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Size not found with id: " + id)
        );
        sizeFound.setName(sizeRequest.name());
        sizeFound.setDiameterCm(sizeRequest.diameterCm());
        sizeFound.setPriceMultiplier(sizeRequest.priceMultiplier());
        sizeFound.setActive(sizeRequest.active());
        return sizeMapper.toDto(sizeRepository.save(sizeFound));
    }

    @Override
    public void deleteSize(Long id) {
        Size sizeFound = sizeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Size not found with id: " + id)
        );
        sizeRepository.delete(sizeFound);
    }
}
