package com.cibertec.dto;

import lombok.Builder;

@Builder
public record UserRoleResponse(
        UserResponse user,
        RoleResponse role
) {
}
