package com.main.model.dto.response;

import com.main.model.enums.UserRole;

import java.util.UUID;

public record LoginResponseDTO(
        String token,
        UUID userId,
        String email,
        UUID companyId,
        UserRole role
) {
}
