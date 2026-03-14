package com.main.service.impl;

import com.main.infrastructure.exeptions.BusinessRuleException;
import com.main.infrastructure.generic.service.impl.DefaultGenericService;
import com.main.infrastructure.security.AuthorizationService;
import com.main.model.dto.request.UserRequestDTO;
import com.main.model.dto.response.UserResponseDTO;
import com.main.model.entity.Company;
import com.main.model.entity.User;
import com.main.model.enums.UserRole;
import com.main.model.mapper.UserMapper;
import com.main.repository.CompanyRepository;
import com.main.repository.UserRepository;
import com.main.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class DefaultUserService extends DefaultGenericService<User, UserRequestDTO, UserResponseDTO> implements UserService {

    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorizationService authorizationService;

    public DefaultUserService(UserRepository repository, UserMapper mapper, CompanyRepository companyRepository, PasswordEncoder passwordEncoder, AuthorizationService authorizationService) {
        super(repository, mapper);
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorizationService = authorizationService;
    }

    @Override
    @Transactional
    public UserResponseDTO create(UserRequestDTO request) {
        if (request.companyId() != null) {
            authorizationService.requireCompanyAccess(request.companyId());
        } else {
            authorizationService.requireSuperAdmin();
        }
        validateWaiterLimit(request);
        User entity = mapper.toEntity(request);
        if (request.companyId() != null) {
            Company company = companyRepository.findById(request.companyId())
                    .orElseThrow(() -> new BusinessRuleException("Empresa não encontrada"));
            entity.setCompany(company);
        }
        if (request.password() != null && !request.password().isBlank()) {
            entity.setPasswordHash(passwordEncoder.encode(request.password()));
        } else if (entity.getPasswordHash() == null || entity.getPasswordHash().isBlank()) {
            throw new BusinessRuleException("Senha é obrigatória");
        }
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public UserResponseDTO update(UUID id, UserRequestDTO request) {
        User entity = repository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("Registro não encontrado para atualização"));
        UUID resourceCompanyId = request.companyId() != null ? request.companyId() : (entity.getCompany() != null ? entity.getCompany().getId() : null);
        if (resourceCompanyId != null) {
            authorizationService.requireCompanyAccess(resourceCompanyId);
        } else {
            authorizationService.requireSuperAdmin();
        }
        validateWaiterLimit(request, entity);
        mapper.updateEntity(request, entity);
        if (request.companyId() != null) {
            Company company = companyRepository.findById(request.companyId())
                    .orElseThrow(() -> new BusinessRuleException("Empresa não encontrada"));
            entity.setCompany(company);
        } else {
            entity.setCompany(null);
        }
        if (request.password() != null && !request.password().isBlank()) {
            entity.setPasswordHash(passwordEncoder.encode(request.password()));
        }
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public UserResponseDTO findById(UUID id) {
        User entity = repository.findById(id).orElseThrow(() -> new BusinessRuleException("Registro não encontrado"));
        if (entity.getCompany() != null) {
            authorizationService.requireCompanyAccess(entity.getCompany().getId());
        } else {
            authorizationService.requireSuperAdmin();
        }
        return mapper.toResponse(entity);
    }

    @Override
    public List<UserResponseDTO> findByCompanyId(UUID companyId) {
        authorizationService.requireCompanyAccess(companyId);
        return mapper.toResponseList(((UserRepository) repository).findByCompany_Id(companyId));
    }

    @Override
    public void delete(UUID id) {
        User entity = repository.findById(id).orElseThrow(() -> new BusinessRuleException("Registro não encontrado"));
        if (entity.getCompany() != null) {
            authorizationService.requireCompanyAccess(entity.getCompany().getId());
        } else {
            authorizationService.requireSuperAdmin();
        }
        repository.deleteById(id);
    }

    private void validateWaiterLimit(UserRequestDTO request) {
        validateWaiterLimit(request, null);
    }

    private void validateWaiterLimit(UserRequestDTO request, User existingUser) {
        if (request.role() != UserRole.WAITER || request.companyId() == null) {
            return;
        }
        if (existingUser != null && existingUser.getRole() == UserRole.WAITER
                && existingUser.getCompany() != null
                && existingUser.getCompany().getId().equals(request.companyId())) {
            return;
        }
        Company company = companyRepository.findById(request.companyId())
                .orElseThrow(() -> new BusinessRuleException("Empresa não encontrada"));
        int max = company.getMaxWaiters() != null ? company.getMaxWaiters() : 10;
        long current = ((UserRepository) repository).countActiveWaitersByCompany(request.companyId(), UserRole.WAITER);
        if (current >= max) {
            throw new BusinessRuleException("Limite de garçons atingido para esta empresa. Máximo: " + max);
        }
    }
}
