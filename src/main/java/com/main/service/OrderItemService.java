package com.main.service;

import com.main.infrastructure.generic.service.GenericService;
import com.main.model.dto.request.OrderItemRequestDTO;
import com.main.model.dto.response.OrderItemResponseDTO;

import java.util.UUID;

public interface OrderItemService extends GenericService<OrderItemRequestDTO, OrderItemResponseDTO> {

    OrderItemResponseDTO cancel(UUID orderItemId);
}
