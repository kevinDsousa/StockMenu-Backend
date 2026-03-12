package com.main.service.impl;

import com.main.infrastructure.exeptions.BusinessRuleException;
import com.main.infrastructure.generic.service.impl.DefaultGenericService;
import com.main.model.dto.request.ProductRequestDTO;
import com.main.model.dto.response.ProductResponseDTO;
import com.main.model.entity.Company;
import com.main.model.entity.PrimaryProduct;
import com.main.model.entity.Product;
import com.main.model.mapper.ProductMapper;
import com.main.repository.PrimaryProductRepository;
import com.main.repository.ProductRepository;
import com.main.repository.CompanyRepository;
import com.main.service.ImageService;
import com.main.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class DefaultProductService extends DefaultGenericService<Product, ProductRequestDTO, ProductResponseDTO> implements ProductService {

    private final CompanyRepository companyRepository;
    private final PrimaryProductRepository primaryProductRepository;
    private final ImageService imageService;

    public DefaultProductService(ProductRepository repository, ProductMapper mapper,
                                CompanyRepository companyRepository, PrimaryProductRepository primaryProductRepository,
                                ImageService imageService) {
        super(repository, mapper);
        this.companyRepository = companyRepository;
        this.primaryProductRepository = primaryProductRepository;
        this.imageService = imageService;
    }

    @Override
    @Transactional
    public ProductResponseDTO create(ProductRequestDTO request) {
        Product entity = mapper.toEntity(request);
        Company company = companyRepository.findById(request.companyId())
                .orElseThrow(() -> new BusinessRuleException("Empresa não encontrada"));
        PrimaryProduct primaryProduct = primaryProductRepository.findById(request.primaryProductId())
                .orElseThrow(() -> new BusinessRuleException("Produto primário não encontrado"));
        entity.setCompany(company);
        entity.setPrimaryProduct(primaryProduct);
        if (request.image() != null && request.image().length > 0) {
            String context = request.companyId() + "/products";
            String key = imageService.uploadImageBytes(request.image(),
                    request.imageContentType() != null ? request.imageContentType() : "image/jpeg",
                    context);
            entity.setImageUrl(key);
        }
        Product saved = repository.save(entity);
        return enrichWithImageUrl(mapper.toResponse(saved));
    }

    @Override
    @Transactional
    public ProductResponseDTO update(UUID id, ProductRequestDTO request) {
        Product entity = repository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("Registro não encontrado para atualização"));
        mapper.updateEntity(request, entity);
        Company company = companyRepository.findById(request.companyId())
                .orElseThrow(() -> new BusinessRuleException("Empresa não encontrada"));
        PrimaryProduct primaryProduct = primaryProductRepository.findById(request.primaryProductId())
                .orElseThrow(() -> new BusinessRuleException("Produto primário não encontrado"));
        entity.setCompany(company);
        entity.setPrimaryProduct(primaryProduct);
        if (request.image() != null && request.image().length > 0) {
            String context = request.companyId() + "/products";
            String key = imageService.uploadImageBytes(request.image(),
                    request.imageContentType() != null ? request.imageContentType() : "image/jpeg",
                    context);
            entity.setImageUrl(key);
        }
        Product saved = repository.save(entity);
        return enrichWithImageUrl(mapper.toResponse(saved));
    }

    @Override
    public ProductResponseDTO findById(UUID id) {
        return enrichWithImageUrl(super.findById(id));
    }

    @Override
    public List<ProductResponseDTO> findAll() {
        return super.findAll().stream()
                .map(this::enrichWithImageUrl)
                .toList();
    }

    @Override
    public List<ProductResponseDTO> findByCompanyId(UUID companyId) {
        return ((ProductRepository) repository).findByCompany_Id(companyId).stream()
                .map(e -> enrichWithImageUrl(mapper.toResponse(e)))
                .toList();
    }

    private ProductResponseDTO enrichWithImageUrl(ProductResponseDTO response) {
        if (response == null) return null;
        if (response.getImageUrl() != null && !response.getImageUrl().isBlank()) {
            response.setImageUrl(imageService.getImageUrl(response.getImageUrl()));
        }
        return response;
    }
}
