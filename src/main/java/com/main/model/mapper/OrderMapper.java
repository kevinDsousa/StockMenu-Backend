package com.main.model.mapper;

import com.main.infrastructure.generic.model.mapper.GenericMapper;
import com.main.model.dto.request.OrderRequestDTO;
import com.main.model.dto.response.OrderResponseDTO;
import com.main.infrastructure.generic.model.mapper.CentralMapperConfig;
import com.main.model.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", config = CentralMapperConfig.class)
public interface OrderMapper extends GenericMapper<Order, OrderRequestDTO, OrderResponseDTO> {

    @Override
    @Mappings({
            @Mapping(target = "companyId", source = "company.id"),
            @Mapping(target = "tableId", source = "venueTable.id"),
            @Mapping(target = "tableNumber", source = "venueTable.tableNumber"),
            @Mapping(target = "createdByUserId", source = "createdByUser.id"),
            @Mapping(target = "createdByUserName", source = "createdByUser.name"),
            @Mapping(target = "items", ignore = true)
    })
    OrderResponseDTO toResponse(Order entity);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntity(OrderRequestDTO dto, @MappingTarget Order entity);
}
