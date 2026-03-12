package com.main.model.dto.request;

import com.main.model.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UserRequestDTO(
        UUID companyId,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        @Size(max = 255)
        String email,

        @Size(max = 100)
        String password,

        @Size(max = 150)
        String name,

        @NotNull(message = "Role is required")
        UserRole role
) {
}
