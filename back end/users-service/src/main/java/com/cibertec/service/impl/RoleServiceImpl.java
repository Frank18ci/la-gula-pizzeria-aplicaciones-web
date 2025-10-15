package com.cibertec.service.impl;

import com.cibertec.dto.RoleRequest;
import com.cibertec.dto.RoleResponse;
import com.cibertec.exception.ResourceNotFound;
import com.cibertec.model.Role;
import com.cibertec.repository.RoleRepository;
import com.cibertec.service.RoleService;
import com.cibertec.util.RoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    @Override
    public List<RoleResponse> getAllRoles() {
        return roleMapper.toDtoList(roleRepository.findAll());
    }

    @Override
    public RoleResponse getRoleById(Long id) {
        return roleMapper.toDto(roleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Role not found with id: " + id)
        ));
    }

    @Override
    public RoleResponse createRole(RoleRequest roleRequest) {
        return roleMapper.toDto(roleRepository.save(roleMapper.toEntity(roleRequest)));
    }

    @Override
    public RoleResponse updateRole(Long id, RoleRequest roleRequest) {
        Role roleFound = roleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Role not found with id: " + id)
        );
        roleFound.setName(roleRequest.name());
        if (roleRequest.name() != null) roleFound.setDescription(roleRequest.description());

        return roleMapper.toDto(roleRepository.save(roleFound));
    }

    @Override
    public void deleteRole(Long id) {
        Role roleFound = roleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("Role not found with id: " + id)
        );
        roleRepository.delete(roleFound);
    }
}
