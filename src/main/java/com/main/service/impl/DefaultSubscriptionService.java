package com.main.service.impl;

import com.main.infrastructure.generic.service.impl.DefaultGenericService;
import com.main.model.dto.request.SubscriptionRequestDTO;
import com.main.model.dto.response.SubscriptionResponseDTO;
import com.main.model.entity.Subscription;
import com.main.model.mapper.SubscriptionMapper;
import com.main.repository.SubscriptionRepository;
import com.main.service.SubscriptionService;
import org.springframework.stereotype.Service;

@Service
public class DefaultSubscriptionService extends DefaultGenericService<Subscription, SubscriptionRequestDTO, SubscriptionResponseDTO> implements SubscriptionService {

    public DefaultSubscriptionService(SubscriptionRepository repository, SubscriptionMapper mapper) {
        super(repository, mapper);
    }
}
