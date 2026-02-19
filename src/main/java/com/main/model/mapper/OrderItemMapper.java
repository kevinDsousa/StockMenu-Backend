package com.main.model.mapper;

import com.main.infrastructure.generic.model.mapper.GenericMapper;
import com.main.model.dto.request.OrderItemRequestDTO;
import com.main.model.dto.response.OrderItemResponseDTO;
import com.main.model.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderItemMapper extends GenericMapper<OrderItem, OrderItemRequestDTO, OrderItemResponseDTO> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntity(OrderItemRequestDTO request, @MappingTarget OrderItem orderItem);
}
