package com.main.service;

import com.main.infrastructure.generic.service.GenericService;
import com.main.model.dto.request.PaymentMethodRequestDTO;
import com.main.model.dto.response.PaymentMethodResponseDTO;

import java.util.List;
import java.util.UUID;

public interface PaymentMethodService extends GenericService<PaymentMethodRequestDTO, PaymentMethodResponseDTO> {

    List<PaymentMethodResponseDTO> findByCompanyId(UUID companyId);
}
