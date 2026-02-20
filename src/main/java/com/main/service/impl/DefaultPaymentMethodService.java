package com.main.service.impl;

import com.main.infrastructure.generic.service.impl.DefaultGenericService;
import com.main.model.dto.request.PaymentMethodRequestDTO;
import com.main.model.dto.response.PaymentMethodResponseDTO;
import com.main.model.entity.PaymentMethod;
import com.main.model.mapper.PaymentMethodMapper;
import com.main.repository.PaymentMethodRepository;
import com.main.service.PaymentMethodService;
import org.springframework.stereotype.Service;

@Service
public class DefaultPaymentMethodService extends DefaultGenericService<PaymentMethod, PaymentMethodRequestDTO, PaymentMethodResponseDTO> implements PaymentMethodService {

    public DefaultPaymentMethodService(PaymentMethodRepository repository, PaymentMethodMapper mapper) {
        super(repository, mapper);
    }
}
