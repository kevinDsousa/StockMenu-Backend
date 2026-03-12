package com.main.service;

import com.main.infrastructure.generic.service.GenericService;
import com.main.model.dto.request.UnitMeasureRequestDTO;
import com.main.model.dto.response.UnitMeasureResponseDTO;

import java.util.List;
import java.util.UUID;

public interface UnitMeasureService extends GenericService<UnitMeasureRequestDTO, UnitMeasureResponseDTO> {

    @Override
    UnitMeasureResponseDTO create(UnitMeasureRequestDTO request);

    @Override
    UnitMeasureResponseDTO update(UUID id, UnitMeasureRequestDTO request);

    @Override
    UnitMeasureResponseDTO findById(UUID id);

    @Override
    List<UnitMeasureResponseDTO> findAll();

    @Override
    void delete(UUID id);
}
