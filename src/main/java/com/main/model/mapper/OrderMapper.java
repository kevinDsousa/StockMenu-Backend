package com.main.model.mapper;

import com.main.infrastructure.generic.model.mapper.GenericMapper;
import com.main.model.dto.request.OrderRequestDTO;
import com.main.model.dto.response.OrderResponseDTO;
import com.main.model.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderMapper extends GenericMapper<Order, OrderRequestDTO, OrderResponseDTO> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntity(OrderRequestDTO dto, @MappingTarget Order entity);
}
