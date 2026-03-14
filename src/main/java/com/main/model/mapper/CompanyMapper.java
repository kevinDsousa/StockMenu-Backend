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
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "subscriptions", ignore = true)
    Company toEntity(CompanyRequestDTO request);

    @Override
    @Mapping(target = "canOperate", expression = "java(entity.canOperate())")
    CompanyResponseDTO toResponse(Company entity);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "subscriptions", ignore = true)
    void updateEntity(CompanyRequestDTO request, @MappingTarget Company entity);
}