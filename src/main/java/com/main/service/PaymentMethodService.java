package com.main.service;

import com.main.infrastructure.generic.service.GenericService;
import com.main.model.dto.request.PaymentMethodRequestDTO;
import com.main.model.dto.response.PaymentMethodResponseDTO;

public interface PaymentMethodService extends GenericService<PaymentMethodRequestDTO, PaymentMethodResponseDTO> {
}
