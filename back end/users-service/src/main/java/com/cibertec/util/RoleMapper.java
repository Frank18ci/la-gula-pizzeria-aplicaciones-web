package com.cibertec.util;

import com.cibertec.dto.RoleRequest;
import com.cibertec.dto.RoleResponse;
import com.cibertec.model.Role;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toEntity(RoleRequest roleRequest);
    RoleResponse toDto(Role role);
    List<RoleResponse> toDtoList(List<Role> roles);
}
