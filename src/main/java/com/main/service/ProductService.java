package com.main.service;

import com.main.infrastructure.generic.service.GenericService;
import com.main.model.dto.request.ProductRequestDTO;
import com.main.model.dto.response.ProductResponseDTO;

import java.util.List;
import java.util.UUID;

public interface ProductService extends GenericService<ProductRequestDTO, ProductResponseDTO> {

    List<ProductResponseDTO> findByCompanyId(UUID companyId);
}
