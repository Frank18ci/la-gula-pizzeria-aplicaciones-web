package com.cibertec.util;

import com.cibertec.dto.UserRoleRequest;
import com.cibertec.dto.UserRoleResponse;
import com.cibertec.model.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {
    @Mapping(source = "userId", target = "id.userId")
    @Mapping(source = "roleId", target = "id.roleId")
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "roleId", target = "role.id")
    UserRole toEntity(UserRoleRequest userRequest);
    UserRoleResponse toDto(UserRole userRole);
    List<UserRoleResponse> toDtoList(List<UserRole> userRoles);
}
