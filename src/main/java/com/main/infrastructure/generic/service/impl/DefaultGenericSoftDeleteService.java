package com.main.infrastructure.generic.service.impl;

import com.main.infrastructure.exeptions.BusinessRuleException;
import com.main.infrastructure.generic.model.mapper.GenericMapper;
import com.main.infrastructure.generic.service.GenericSoftDeleteService;
import com.main.infrastructure.generic.model.entity.GenericEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.UUID;

public abstract class DefaultGenericSoftDeleteService<E extends GenericEntity, RQ, RS>
        extends DefaultGenericService<E, RQ, RS>
        implements GenericSoftDeleteService<RS> {

    protected DefaultGenericSoftDeleteService(JpaRepository<E, UUID> repository, GenericMapper<E, RQ, RS> mapper) {
        super(repository, mapper);
    }

    @Override
    public RS findByIdIncludingDeleted(UUID id) {
        return findEntityIncludingDeleted(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new BusinessRuleException("Registro não encontrado em nenhum estado."));
    }

    @Override
    @Transactional
    public RS restore(UUID id) {
        E entity = findEntityIncludingDeleted(id)
                .orElseThrow(() -> new BusinessRuleException("Registro não encontrado para restauração."));

        if (entity.getDeletedAt() == null) {
            throw new BusinessRuleException("Este registro já está ativo.");
        }

        entity.setDeletedAt(null);
        return mapper.toResponse(repository.save(entity));
    }

    protected abstract Optional<E> findEntityIncludingDeleted(UUID id);
}