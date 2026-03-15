package com.main.service;

import com.main.infrastructure.generic.service.GenericService;
import com.main.model.dto.request.OrderItemRequestDTO;
import com.main.model.dto.response.OrderItemResponseDTO;

import java.util.List;
import java.util.UUID;

public interface OrderItemService extends GenericService<OrderItemRequestDTO, OrderItemResponseDTO> {

    List<OrderItemResponseDTO> findByOrderId(UUID orderId);

    OrderItemResponseDTO cancel(UUID orderItemId);
}
