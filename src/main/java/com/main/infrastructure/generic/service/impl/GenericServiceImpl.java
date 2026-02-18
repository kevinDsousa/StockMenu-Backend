package com.main.infrastructure.generic.service.impl;

import com.main.infrastructure.generic.model.mapper.GenericMapper;
import com.main.infrastructure.generic.service.GenericService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class GenericServiceImpl<E, RQ, RS> implements GenericService<RQ, RS> {

    protected abstract JpaRepository<E, UUID> getRepository();
    protected abstract GenericMapper<E, RQ, RS> getMapper();

    @Override
    public RS create(RQ request) {
        E entity = getMapper().toEntity(request);
        entity = getRepository().save(entity);
        return getMapper().toResponse(entity);
    }

    @Override
    public RS update(UUID id, RQ request) {
        E entity = getRepository().findById(id)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado"));

        getMapper().updateEntity(request, entity);

        entity = getRepository().save(entity);
        return getMapper().toResponse(entity);
    }

    @Override
    public RS findById(UUID id) {
        E entity = getRepository().findById(id)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado"));

        return getMapper().toResponse(entity);
    }

    @Override
    public List<RS> findAll() {
        return getRepository().findAll()
                .stream()
                .map(getMapper()::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID id) {
        getRepository().deleteById(id);
    }
}