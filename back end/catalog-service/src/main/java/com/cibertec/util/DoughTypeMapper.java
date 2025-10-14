package com.cibertec.util;

import com.cibertec.dto.DoughTypeRequest;
import com.cibertec.dto.DoughTypeResponse;
import com.cibertec.model.DoughType;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DoughTypeMapper {
    DoughType toEntity(DoughTypeRequest doughTypeRequest);
    DoughTypeResponse toDto(DoughType doughType);
    List<DoughTypeResponse> toDtoList(List<DoughType> doughTypes);
}
