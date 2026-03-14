package com.main.infrastructure.security;

import java.util.UUID;

public interface AuthorizationService {

    StockMenuPrincipal getCurrentPrincipal();

    void requireCompanyAccess(UUID companyId);

    void requireSuperAdmin();
}
