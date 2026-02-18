package com.main.service;

import com.main.infrastructure.generic.service.GenericService;
import com.main.model.dto.request.OrderItemRequestDTO;
import com.main.model.dto.response.OrderItemResponseDTO;

public interface OrderItemService extends GenericService<OrderItemRequestDTO, OrderItemResponseDTO> {
}
