package com.main.service.impl;

import com.main.infrastructure.generic.service.impl.DefaultGenericService;
import com.main.model.dto.request.CompanyRequestDTO;
import com.main.model.dto.response.CompanyResponseDTO;
import com.main.model.entity.Company;
import com.main.model.mapper.CompanyMapper;
import com.main.repository.CompanyRepository;
import com.main.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class DefaultCompanyService extends DefaultGenericService<Company, CompanyRequestDTO, CompanyResponseDTO> implements CompanyService{

    public DefaultCompanyService(CompanyRepository repository, CompanyMapper mapper) {
        super(repository, mapper);
    }
}
