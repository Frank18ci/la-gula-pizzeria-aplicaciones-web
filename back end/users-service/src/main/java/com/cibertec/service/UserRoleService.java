package com.cibertec.service;

import com.cibertec.dto.UserRoleRequest;
import com.cibertec.dto.UserRoleResponse;

import java.util.List;

public interface UserRoleService {
    List<UserRoleResponse> getAllUserRoles();
    UserRoleResponse getUserRoleById(Long userId, Long roleId);
    UserRoleResponse createUserRole(UserRoleRequest userRoleRequest);
    void deleteUserRole(Long userId, Long roleId);

    List<UserRoleResponse> getUserRolesByUserId(Long idUser);
}
