package com.cibertec.controller;

import com.cibertec.dto.UserRoleRequest;
import com.cibertec.service.UserRoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-roles")
public class UserRoleController {
    private final UserRoleService userRoleService;
    @GetMapping
    public ResponseEntity<?> getAllUserRoles() {
        return ResponseEntity.ok(userRoleService.getAllUserRoles());
    }
    @GetMapping("/{idUser}/{idRole}")
    public ResponseEntity<?> getUserRoleById(@PathVariable Long idUser, @PathVariable Long idRole) {
        return ResponseEntity.ok(userRoleService.getUserRoleById(idUser, idRole));
    }
    @PostMapping
    public ResponseEntity<?> addUserRole(@RequestBody @Valid UserRoleRequest userRoleRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userRoleService.createUserRole(userRoleRequest));
    }

    @DeleteMapping("/{idUser}/{idRole}")
    public ResponseEntity<?> deleteUserRole(@PathVariable Long idUser, @PathVariable Long idRole) {
        userRoleService.deleteUserRole(idUser, idRole);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/user/{idUser}")
    public ResponseEntity<?> getUserRolesByUserId(@PathVariable Long idUser) {
        return ResponseEntity.ok(userRoleService.getUserRolesByUserId(idUser));
    }
}
