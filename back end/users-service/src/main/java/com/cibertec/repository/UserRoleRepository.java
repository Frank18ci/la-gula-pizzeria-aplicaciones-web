package com.cibertec.repository;

import com.cibertec.model.UserRole;
import com.cibertec.model.util.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
    List<UserRole> findByUserId(Long idUser);
}
