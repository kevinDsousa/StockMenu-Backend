package com.main.infrastructure.security;

import com.main.infrastructure.exeptions.BusinessRuleException;
import com.main.model.enums.UserRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DefaultAuthorizationService implements AuthorizationService {

    @Override
    public StockMenuPrincipal getCurrentPrincipal() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || !(auth.getPrincipal() instanceof StockMenuPrincipal principal)) {
            throw new BusinessRuleException("Usuário não autenticado. Faça login para acessar este recurso.");
        }
        return principal;
    }

    @Override
    public void requireCompanyAccess(UUID companyId) {
        StockMenuPrincipal principal = getCurrentPrincipal();
        if (principal.role() == UserRole.SUPER_ADMIN) {
            return;
        }
        if (companyId == null) {
            throw new BusinessRuleException("Este recurso não está vinculado a nenhuma empresa. Apenas super administrador pode acessá-lo.");
        }
        if (principal.companyId() == null || !principal.companyId().equals(companyId)) {
            throw new BusinessRuleException("Você não tem permissão para acessar ou alterar este recurso. Ele pertence a outra empresa.");
        }
    }

    @Override
    public void requireSuperAdmin() {
        if (getCurrentPrincipal().role() != UserRole.SUPER_ADMIN) {
            throw new BusinessRuleException("Esta ação é restrita ao super administrador. Você não tem permissão para realizá-la.");
        }
    }
}
