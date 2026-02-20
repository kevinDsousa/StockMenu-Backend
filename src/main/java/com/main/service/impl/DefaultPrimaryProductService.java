package com.main.service.impl;

import com.main.infrastructure.generic.service.impl.DefaultGenericService;
import com.main.model.dto.request.PrimaryProductRequestDTO;
import com.main.model.dto.response.PrimaryProductResponseDTO;
import com.main.model.entity.PrimaryProduct;
import com.main.model.mapper.PrimaryProductMapper;
import com.main.repository.PrimaryProductRepository;
import com.main.service.PrimaryProductService;
import org.springframework.stereotype.Service;

@Service
public class DefaultPrimaryProductService extends DefaultGenericService<PrimaryProduct, PrimaryProductRequestDTO, PrimaryProductResponseDTO> implements PrimaryProductService {

    public DefaultPrimaryProductService(PrimaryProductRepository repository, PrimaryProductMapper mapper) {
        super(repository, mapper);
    }
}
