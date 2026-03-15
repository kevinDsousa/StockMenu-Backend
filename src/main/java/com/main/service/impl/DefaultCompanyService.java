package com.main.service.impl;

import com.main.infrastructure.exeptions.BusinessRuleException;
import com.main.infrastructure.generic.service.impl.DefaultGenericService;
import com.main.infrastructure.security.AuthorizationService;
import com.main.model.dto.request.CompanyRequestDTO;
import com.main.model.dto.response.CompanyResponseDTO;
import com.main.model.entity.Company;
import com.main.model.mapper.CompanyMapper;
import com.main.repository.CompanyRepository;
import com.main.service.CompanyService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DefaultCompanyService extends DefaultGenericService<Company, CompanyRequestDTO, CompanyResponseDTO> implements CompanyService {

    private final AuthorizationService authorizationService;

    public DefaultCompanyService(CompanyRepository repository, CompanyMapper mapper, AuthorizationService authorizationService) {
        super(repository, mapper);
        this.authorizationService = authorizationService;
    }

    @Override
    public CompanyResponseDTO create(CompanyRequestDTO request) {
        authorizationService.requireSuperAdmin();
        return super.create(request);
    }

    @Override
    public CompanyResponseDTO findById(UUID id) {
        CompanyRepository companyRepository = (CompanyRepository) repository;
        Company entity = companyRepository.findByIdWithSubscriptions(id)
                .orElseThrow(() -> new BusinessRuleException("Registro não encontrado"));
        authorizationService.requireCompanyAccess(entity.getId());
        return mapper.toResponse(entity);
    }

    @Override
    public CompanyResponseDTO update(UUID id, CompanyRequestDTO request) {
        authorizationService.requireCompanyAccess(id);
        return super.update(id, request);
    }

    @Override
    public void delete(UUID id) {
        authorizationService.requireCompanyAccess(id);
        repository.deleteById(id);
    }
}
