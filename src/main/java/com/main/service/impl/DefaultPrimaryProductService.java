package com.main.service.impl;

import com.main.infrastructure.exeptions.BusinessRuleException;
import com.main.infrastructure.generic.service.impl.DefaultGenericService;
import com.main.infrastructure.security.AuthorizationService;
import com.main.model.dto.request.PrimaryProductRequestDTO;
import com.main.model.dto.response.PrimaryProductResponseDTO;
import com.main.model.entity.Company;
import com.main.model.entity.PrimaryProduct;
import com.main.model.mapper.PrimaryProductMapper;
import com.main.repository.CompanyRepository;
import com.main.repository.PrimaryProductRepository;
import com.main.repository.UnitMeasureRepository;
import com.main.service.ImageService;
import com.main.service.PrimaryProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class DefaultPrimaryProductService extends DefaultGenericService<PrimaryProduct, PrimaryProductRequestDTO, PrimaryProductResponseDTO> implements PrimaryProductService {

    private final CompanyRepository companyRepository;
    private final ImageService imageService;
    private final UnitMeasureRepository unitMeasureRepository;
    private final AuthorizationService authorizationService;

    public DefaultPrimaryProductService(PrimaryProductRepository repository, PrimaryProductMapper mapper,
                                       CompanyRepository companyRepository, ImageService imageService,
                                       UnitMeasureRepository unitMeasureRepository, AuthorizationService authorizationService) {
        super(repository, mapper);
        this.companyRepository = companyRepository;
        this.imageService = imageService;
        this.unitMeasureRepository = unitMeasureRepository;
        this.authorizationService = authorizationService;
    }

    @Override
    @Transactional
    public PrimaryProductResponseDTO create(PrimaryProductRequestDTO request) {
        authorizationService.requireCompanyAccess(request.companyId());
        unitMeasureRepository.findByKeyAndDeletedAtIsNull(request.unit())
                .filter(u -> u.isActive())
                .orElseThrow(() -> new BusinessRuleException("Unidade de medida não encontrada ou inativa."));
        PrimaryProduct entity = mapper.toEntity(request);
        Company company = companyRepository.findById(request.companyId())
                .orElseThrow(() -> new BusinessRuleException("Empresa não encontrada"));
        entity.setCompany(company);
        if (request.image() != null && request.image().length > 0) {
            String context = request.companyId() + "/primary-products";
            String key = imageService.uploadImageBytes(request.image(),
                    request.imageContentType() != null ? request.imageContentType() : "image/jpeg",
                    context);
            entity.setImageUrl(key);
        }
        PrimaryProduct saved = repository.save(entity);
        return enrichWithImageUrl(mapper.toResponse(saved));
    }

    @Override
    @Transactional
    public PrimaryProductResponseDTO update(UUID id, PrimaryProductRequestDTO request) {
        authorizationService.requireCompanyAccess(request.companyId());
        unitMeasureRepository.findByKeyAndDeletedAtIsNull(request.unit())
                .filter(u -> u.isActive())
                .orElseThrow(() -> new BusinessRuleException("Unidade de medida não encontrada ou inativa."));
        PrimaryProduct entity = repository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("Registro não encontrado para atualização"));
        mapper.updateEntity(request, entity);
        Company company = companyRepository.findById(request.companyId())
                .orElseThrow(() -> new BusinessRuleException("Empresa não encontrada"));
        entity.setCompany(company);
        if (request.image() != null && request.image().length > 0) {
            String context = request.companyId() + "/primary-products";
            String key = imageService.uploadImageBytes(request.image(),
                    request.imageContentType() != null ? request.imageContentType() : "image/jpeg",
                    context);
            entity.setImageUrl(key);
        }
        PrimaryProduct saved = repository.save(entity);
        return enrichWithImageUrl(mapper.toResponse(saved));
    }

    @Override
    public PrimaryProductResponseDTO findById(UUID id) {
        PrimaryProduct entity = repository.findById(id).orElseThrow(() -> new BusinessRuleException("Registro não encontrado"));
        authorizationService.requireCompanyAccess(entity.getCompany().getId());
        return enrichWithImageUrl(mapper.toResponse(entity));
    }

    @Override
    public List<PrimaryProductResponseDTO> findAll() {
        return super.findAll().stream()
                .map(this::enrichWithImageUrl)
                .toList();
    }

    @Override
    public void delete(UUID id) {
        PrimaryProduct entity = repository.findById(id).orElseThrow(() -> new BusinessRuleException("Registro não encontrado"));
        authorizationService.requireCompanyAccess(entity.getCompany().getId());
        repository.deleteById(id);
    }

    @Override
    public List<PrimaryProductResponseDTO> findByCompanyId(UUID companyId) {
        authorizationService.requireCompanyAccess(companyId);
        return ((PrimaryProductRepository) repository).findByCompany_Id(companyId).stream()
                .map(e -> enrichWithImageUrl(mapper.toResponse(e)))
                .toList();
    }

    private PrimaryProductResponseDTO enrichWithImageUrl(PrimaryProductResponseDTO response) {
        if (response == null) return null;
        if (response.getImageUrl() != null && !response.getImageUrl().isBlank()) {
            response.setImageUrl(imageService.getImageUrl(response.getImageUrl()));
        }
        return response;
    }
}
