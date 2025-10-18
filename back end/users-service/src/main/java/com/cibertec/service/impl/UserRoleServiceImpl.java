package com.cibertec.service.impl;

import com.cibertec.dto.UserRoleRequest;
import com.cibertec.dto.UserRoleResponse;
import com.cibertec.exception.ResourceNotFound;
import com.cibertec.model.UserRole;
import com.cibertec.model.util.UserRoleId;
import com.cibertec.repository.UserRoleRepository;
import com.cibertec.service.UserRoleService;
import com.cibertec.util.UserRoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository userRoleRepository;
    private final UserRoleMapper userRoleMapper;
    @Override
    public List<UserRoleResponse> getAllUserRoles() {
        return userRoleMapper.toDtoList(userRoleRepository.findAll());
    }

    @Override
    public UserRoleResponse getUserRoleById(Long userId, Long roleId) {
        return userRoleMapper.toDto(userRoleRepository.findById(UserRoleId.builder().userId(userId).roleId(roleId).build()).orElseThrow(
                () -> new ResourceNotFound("UserRole not found with userId: " + userId + " and roleId: " + roleId)
        ));
    }

    @Override
    public UserRoleResponse createUserRole(UserRoleRequest userRoleRequest) {
        return userRoleMapper.toDto(userRoleRepository.save(userRoleMapper.toEntity(userRoleRequest)));
    }

    @Override
    public void deleteUserRole(Long userId, Long roleId) {
        UserRole userRoleFound = userRoleRepository.findById(UserRoleId.builder().userId(userId).roleId(roleId).build()).orElseThrow(
                () -> new ResourceNotFound("UserRole not found with userId: " + userId + " and roleId: " + roleId)
        );
        userRoleRepository.delete(userRoleFound);
    }

    @Override
    public List<UserRoleResponse> getUserRolesByUserId(Long idUser) {
        return userRoleMapper.toDtoList(userRoleRepository.findByUserId(idUser));
    }
}
