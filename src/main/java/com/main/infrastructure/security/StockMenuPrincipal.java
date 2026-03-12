package com.main.infrastructure.security;

import com.main.model.enums.UserRole;

import java.util.UUID;

public record StockMenuPrincipal(UUID userId, String email, UUID companyId, UserRole role) {
}
