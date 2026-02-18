package com.main.model.mapper;

import com.main.infrastructure.generic.model.mapper.GenericMapper;
import com.main.model.dto.request.CompanyRequestDTO;
import com.main.model.dto.response.CompanyResponseDTO;
import com.main.model.entity.Company;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CompanyMapper extends GenericMapper<Company, CompanyRequestDTO, CompanyResponseDTO> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntity(CompanyRequestDTO request, @MappingTarget Company entity);
}