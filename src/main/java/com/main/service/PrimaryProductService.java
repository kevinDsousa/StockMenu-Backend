package com.main.service;

import com.main.infrastructure.generic.service.GenericService;
import com.main.model.dto.request.PrimaryProductRequestDTO;
import com.main.model.dto.response.PrimaryProductResponseDTO;

import java.util.List;
import java.util.UUID;

public interface PrimaryProductService extends GenericService<PrimaryProductRequestDTO, PrimaryProductResponseDTO> {

    List<PrimaryProductResponseDTO> findByCompanyId(UUID companyId);
}
