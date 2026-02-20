package com.main.model.mapper;

import com.main.infrastructure.generic.model.mapper.GenericMapper;
import com.main.model.dto.request.SubscriptionRequestDTO;
import com.main.model.dto.response.SubscriptionResponseDTO;
import com.main.model.entity.Subscription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper extends GenericMapper<Subscription, SubscriptionRequestDTO, SubscriptionResponseDTO> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntity(SubscriptionRequestDTO request, @MappingTarget Subscription subscription);
}
