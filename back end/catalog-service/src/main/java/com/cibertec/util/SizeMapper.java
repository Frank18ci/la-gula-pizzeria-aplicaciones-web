package com.cibertec.util;

import com.cibertec.dto.SizeRequest;
import com.cibertec.dto.SizeResponse;
import com.cibertec.model.Size;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SizeMapper {
    Size toEntity(SizeRequest sizeRequest);
    SizeResponse toDto(Size size);
    List<SizeResponse> toDtoList(List<Size> sizes);
}
