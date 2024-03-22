package com.example.turboaz.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequestDTO {
    @NotBlank(message = "USERNAME_MUST_NOT_BE_NULL")
    private String username;
    @NotBlank(message = "PASSWORD_MUST_NOT_BE_NULL")
    private String password;
    @NotNull(message = "ROLE_MUST_NOT_BE_NULL")
    private Set<RoleDTO> roles;
}