package com.main.service;

import com.main.infrastructure.generic.service.GenericService;
import com.main.model.dto.request.OrderRequestDTO;
import com.main.model.dto.response.OrderResponseDTO;

public interface OrderService extends GenericService<OrderRequestDTO, OrderResponseDTO> {
}
