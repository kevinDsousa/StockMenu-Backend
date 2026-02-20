package com.main.service.impl;

import com.main.infrastructure.generic.service.impl.DefaultGenericService;
import com.main.model.dto.request.ProductRequestDTO;
import com.main.model.dto.response.ProductResponseDTO;
import com.main.model.entity.Product;
import com.main.model.mapper.ProductMapper;
import com.main.repository.ProductRepository;
import com.main.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class DefaultProductService extends DefaultGenericService<Product, ProductRequestDTO, ProductResponseDTO> implements ProductService {

    public DefaultProductService(ProductRepository repository, ProductMapper mapper) {
        super(repository, mapper);
    }
}
