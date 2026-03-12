package com.main.service.impl;

import com.main.infrastructure.exeptions.BusinessRuleException;
import com.main.infrastructure.generic.service.impl.DefaultGenericService;
import com.main.model.dto.request.UnitMeasureRequestDTO;
import com.main.model.dto.response.UnitMeasureResponseDTO;
import com.main.model.entity.UnitMeasureEntity;
import com.main.repository.PrimaryProductRepository;
import com.main.repository.UnitMeasureRepository;
import com.main.service.UnitMeasureService;
import com.main.model.mapper.UnitMeasureMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class DefaultUnitMeasureService extends DefaultGenericService<UnitMeasureEntity, UnitMeasureRequestDTO, UnitMeasureResponseDTO> implements UnitMeasureService {

    private final PrimaryProductRepository primaryProductRepository;

    public DefaultUnitMeasureService(UnitMeasureRepository repository, UnitMeasureMapper mapper,
                                     PrimaryProductRepository primaryProductRepository) {
        super(repository, mapper);
        this.primaryProductRepository = primaryProductRepository;
    }

    private UnitMeasureRepository getUnitMeasureRepository() {
        return (UnitMeasureRepository) repository;
    }

    @Override
    public List<UnitMeasureResponseDTO> findAll() {
        return getUnitMeasureRepository().findByDeletedAtIsNullOrderByLabel().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public UnitMeasureResponseDTO create(UnitMeasureRequestDTO request) {
        if (getUnitMeasureRepository().existsByKeyAndDeletedAtIsNull(request.key().trim().toUpperCase())) {
            throw new BusinessRuleException("Já existe uma unidade com o código informado.");
        }
        UnitMeasureEntity entity = mapper.toEntity(request);
        entity.setKey(entity.getKey().trim().toUpperCase());
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public UnitMeasureResponseDTO update(UUID id, UnitMeasureRequestDTO request) {
        UnitMeasureEntity entity = repository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("Unidade não encontrada."));
        String newKey = request.key().trim().toUpperCase();
        if (!entity.getKey().equals(newKey) && getUnitMeasureRepository().existsByKeyAndDeletedAtIsNull(newKey)) {
            throw new BusinessRuleException("Já existe outra unidade com o código informado.");
        }
        mapper.updateEntity(request, entity);
        entity.setKey(newKey);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        UnitMeasureEntity entity = repository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("Unidade não encontrada."));
        long inUse = primaryProductRepository.countByUnitAndDeletedAtIsNull(entity.getKey());
        if (inUse > 0) {
            throw new BusinessRuleException("Não é possível excluir: existem insumos usando esta unidade.");
        }
        repository.deleteById(id);
    }
}
