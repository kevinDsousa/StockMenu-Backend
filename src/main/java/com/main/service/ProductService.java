package com.main.service;

import com.main.infrastructure.generic.service.GenericService;
import com.main.model.dto.request.ProductRequestDTO;
import com.main.model.dto.response.ProductResponseDTO;

public interface ProductService extends GenericService<ProductRequestDTO, ProductResponseDTO> {
}
