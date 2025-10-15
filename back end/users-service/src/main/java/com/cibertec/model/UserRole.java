package com.cibertec.model;

import com.cibertec.model.util.UserRoleId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_roles")
public class UserRole {
    @EmbeddedId
    private UserRoleId id;
    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false)
    private LocalDateTime assignedAt;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(nullable = false)
    private User user;
    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(nullable = false)
    private Role role;
}
