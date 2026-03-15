package com.main.model.mapper;

import com.main.infrastructure.generic.model.mapper.GenericMapper;
import com.main.model.dto.request.PaymentMethodRequestDTO;
import com.main.model.dto.response.PaymentMethodResponseDTO;
import com.main.infrastructure.generic.model.mapper.CentralMapperConfig;
import com.main.model.entity.PaymentMethod;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", config = CentralMapperConfig.class)
public interface PaymentMethodMapper extends GenericMapper<PaymentMethod, PaymentMethodRequestDTO, PaymentMethodResponseDTO> {

    @Override
    @Mapping(target = "companyId", source = "company.id")
    PaymentMethodResponseDTO toResponse(PaymentMethod entity);

    @Override
    @Mapping(target = "company", ignore = true)
    PaymentMethod toEntity(PaymentMethodRequestDTO request);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntity(PaymentMethodRequestDTO request, @MappingTarget PaymentMethod entity);
}
