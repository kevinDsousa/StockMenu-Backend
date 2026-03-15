package com.main.service.impl;

import com.main.infrastructure.exeptions.BusinessRuleException;
import com.main.infrastructure.generic.service.impl.DefaultGenericService;
import com.main.infrastructure.security.AuthorizationService;
import com.main.model.dto.request.PaymentMethodRequestDTO;
import com.main.model.dto.response.PaymentMethodResponseDTO;
import com.main.model.entity.Company;
import com.main.model.entity.PaymentMethod;
import com.main.model.mapper.PaymentMethodMapper;
import com.main.repository.CompanyRepository;
import com.main.repository.PaymentMethodRepository;
import com.main.service.PaymentMethodService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class DefaultPaymentMethodService extends DefaultGenericService<PaymentMethod, PaymentMethodRequestDTO, PaymentMethodResponseDTO> implements PaymentMethodService {

    private final CompanyRepository companyRepository;
    private final AuthorizationService authorizationService;

    public DefaultPaymentMethodService(PaymentMethodRepository repository, PaymentMethodMapper mapper, CompanyRepository companyRepository, AuthorizationService authorizationService) {
        super(repository, mapper);
        this.companyRepository = companyRepository;
        this.authorizationService = authorizationService;
    }

    @Override
    public List<PaymentMethodResponseDTO> findByCompanyId(UUID companyId) {
        authorizationService.requireCompanyAccess(companyId);
        PaymentMethodRepository pmRepository = (PaymentMethodRepository) repository;
        return mapper.toResponseList(pmRepository.findByCompany_Id(companyId));
    }

    @Override
    public PaymentMethodResponseDTO findById(UUID id) {
        PaymentMethod entity = repository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("Registro não encontrado"));
        authorizationService.requireCompanyAccess(entity.getCompany().getId());
        return mapper.toResponse(entity);
    }

    @Override
    public void delete(UUID id) {
        PaymentMethod entity = repository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("Registro não encontrado"));
        authorizationService.requireCompanyAccess(entity.getCompany().getId());
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public PaymentMethodResponseDTO create(PaymentMethodRequestDTO request) {
        authorizationService.requireCompanyAccess(request.companyId());
        Company company = companyRepository.findById(request.companyId())
                .orElseThrow(() -> new BusinessRuleException("Empresa não encontrada"));
        PaymentMethod entity = new PaymentMethod();
        entity.setCompany(company);
        entity.setName(request.name());
        entity.setActive(request.active());
        entity.setAllowsDelivery(request.allowsDelivery());
        entity.setOnlinePayment(request.onlinePayment());
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public PaymentMethodResponseDTO update(UUID id, PaymentMethodRequestDTO request) {
        authorizationService.requireCompanyAccess(request.companyId());
        PaymentMethod entity = repository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("Registro não encontrado para atualização"));
        Company company = companyRepository.findById(request.companyId())
                .orElseThrow(() -> new BusinessRuleException("Empresa não encontrada"));
        mapper.updateEntity(request, entity);
        entity.setCompany(company);
        return mapper.toResponse(repository.save(entity));
    }
}
