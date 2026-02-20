package com.main.model.mapper;

import com.main.infrastructure.generic.model.mapper.GenericMapper;
import com.main.model.dto.request.PrimaryProductRequestDTO;
import com.main.model.dto.response.PrimaryProductResponseDTO;
import com.main.model.entity.PrimaryProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PrimaryProductMapper extends GenericMapper<PrimaryProduct, PrimaryProductRequestDTO, PrimaryProductResponseDTO> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntity(PrimaryProductRequestDTO  request, @MappingTarget PrimaryProduct entity);
}
