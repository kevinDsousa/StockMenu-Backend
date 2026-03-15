package com.main.service.impl;

import com.main.infrastructure.security.AuthorizationService;
import com.main.infrastructure.generic.service.impl.DefaultGenericService;
import com.main.model.dto.request.SubscriptionRequestDTO;
import com.main.model.dto.response.SubscriptionResponseDTO;
import com.main.model.entity.Subscription;
import com.main.model.mapper.SubscriptionMapper;
import com.main.repository.SubscriptionRepository;
import com.main.service.SubscriptionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DefaultSubscriptionService extends DefaultGenericService<Subscription, SubscriptionRequestDTO, SubscriptionResponseDTO> implements SubscriptionService {

    private final AuthorizationService authorizationService;

    public DefaultSubscriptionService(SubscriptionRepository repository, SubscriptionMapper mapper, AuthorizationService authorizationService) {
        super(repository, mapper);
        this.authorizationService = authorizationService;
    }

    @Override
    public SubscriptionResponseDTO create(SubscriptionRequestDTO request) {
        authorizationService.requireSuperAdmin();
        return super.create(request);
    }

    @Override
    public SubscriptionResponseDTO update(UUID id, SubscriptionRequestDTO request) {
        authorizationService.requireSuperAdmin();
        return super.update(id, request);
    }

    @Override
    public SubscriptionResponseDTO findById(UUID id) {
        authorizationService.requireSuperAdmin();
        return super.findById(id);
    }

    @Override
    public List<SubscriptionResponseDTO> findAll() {
        authorizationService.requireSuperAdmin();
        return super.findAll();
    }

    @Override
    public void delete(UUID id) {
        authorizationService.requireSuperAdmin();
        super.delete(id);
    }
}
