package com.main.infrastructure.generic.service.impl;

import com.main.infrastructure.exeptions.BusinessRuleException;
import com.main.infrastructure.generic.model.mapper.GenericMapper;
import com.main.infrastructure.generic.service.GenericService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public abstract class DefaultGenericService<E, RQ, RS> implements GenericService<RQ, RS> {

    protected final JpaRepository<E, UUID> repository;
    protected final GenericMapper<E, RQ, RS> mapper;

    @Override
    public RS create(RQ request) {
        E entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public RS update(UUID id, RQ request) {
        if (!repository.existsById(id)) {
            throw new BusinessRuleException("Registro não encontrado para atualização");
        }
        E entity = mapper.toEntity(request);
        E updatedEntity = repository.save(entity);
        return mapper.toResponse(updatedEntity);
    }

    @Override
    public RS findById(UUID id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new BusinessRuleException("Registro não encontrado"));
    }

    @Override
    public List<RS> findAll() {
        return mapper.toResponseList(repository.findAll());
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

}