package com.main.model.mapper;

import com.main.infrastructure.generic.model.mapper.GenericMapper;
import com.main.model.dto.request.UserRequestDTO;
import com.main.model.dto.response.UserResponseDTO;
import com.main.infrastructure.generic.model.mapper.CentralMapperConfig;
import com.main.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", config = CentralMapperConfig.class)
public interface UserMapper extends GenericMapper<User, UserRequestDTO, UserResponseDTO> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "company", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    User toEntity(UserRequestDTO request);

    @Override
    @Mapping(target = "companyId", source = "company.id")
    UserResponseDTO toResponse(User entity);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "company", ignore = true)
    void updateEntity(UserRequestDTO request, @MappingTarget User entity);
}
