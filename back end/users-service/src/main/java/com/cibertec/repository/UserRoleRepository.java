package com.cibertec.repository;

import com.cibertec.model.UserRole;
import com.cibertec.model.util.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
}
