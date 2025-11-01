package com.cibertec.rabbit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseRabbit implements Serializable {
    private String email;
    private String fullName;
    private String phone;
    private String status;
    private LocalDateTime createdAt;
    private String roleName;
}
