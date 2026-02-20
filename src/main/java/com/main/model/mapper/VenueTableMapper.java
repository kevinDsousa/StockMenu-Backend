package com.main.model.mapper;

import com.main.infrastructure.generic.model.mapper.GenericMapper;
import com.main.model.dto.request.VenueTableRequestDTO;
import com.main.model.dto.response.VenueTableResponseDTO;
import com.main.model.entity.VenueTable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VenueTableMapper extends GenericMapper<VenueTable, VenueTableRequestDTO, VenueTableResponseDTO> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntity(VenueTableRequestDTO request, @MappingTarget VenueTable entity);
}
