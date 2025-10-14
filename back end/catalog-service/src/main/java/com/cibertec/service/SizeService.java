package com.cibertec.service;

import com.cibertec.dto.SizeRequest;
import com.cibertec.dto.SizeResponse;

import java.util.List;

public interface SizeService {
    List<SizeResponse> getSizes();
    SizeResponse getSizeById(Long id);
    SizeResponse createSize(SizeRequest sizeRequest);
    SizeResponse updateSize(Long id, SizeRequest sizeRequest);
    void deleteSize(Long id);
}
