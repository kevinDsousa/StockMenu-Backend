package com.main.model.mapper;

import com.main.infrastructure.generic.model.mapper.GenericMapper;
import com.main.model.dto.request.UnitMeasureRequestDTO;
import com.main.model.dto.response.UnitMeasureResponseDTO;
import com.main.infrastructure.generic.model.mapper.CentralMapperConfig;
import com.main.model.entity.UnitMeasureEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", config = CentralMapperConfig.class)
public interface UnitMeasureMapper extends GenericMapper<UnitMeasureEntity, UnitMeasureRequestDTO, UnitMeasureResponseDTO> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    UnitMeasureEntity toEntity(UnitMeasureRequestDTO request);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntity(UnitMeasureRequestDTO request, @MappingTarget UnitMeasureEntity entity);
}
