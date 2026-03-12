package com.main.service;

import com.main.infrastructure.generic.service.GenericService;
import com.main.model.dto.request.OrderRequestDTO;
import com.main.model.dto.response.OrderResponseDTO;

import java.util.List;
import java.util.UUID;

public interface OrderService extends GenericService<OrderRequestDTO, OrderResponseDTO> {

    List<OrderResponseDTO> findByCompanyId(UUID companyId);

    OrderResponseDTO transferOrderToTable(UUID orderId, UUID targetTableId);
}
