package com.cibertec.client.dto;

import lombok.Builder;

@Builder
public record UserRoleRequest(
        Long userId,
        Long roleId
) {
}
