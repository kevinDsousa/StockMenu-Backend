package com.main.model.mapper;

import com.main.infrastructure.generic.model.mapper.GenericMapper;
import com.main.model.dto.request.PaymentMethodRequestDTO;
import com.main.model.dto.response.PaymentMethodResponseDTO;
import com.main.model.entity.PaymentMethod;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PaymentMethodMapper extends GenericMapper<PaymentMethod, PaymentMethodRequestDTO, PaymentMethodResponseDTO> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntity(PaymentMethodRequestDTO request, @MappingTarget PaymentMethod entity);
}
