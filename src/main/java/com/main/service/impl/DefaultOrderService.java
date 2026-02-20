package com.main.service.impl;

import com.main.infrastructure.generic.service.impl.DefaultGenericService;
import com.main.model.dto.request.OrderRequestDTO;
import com.main.model.dto.response.OrderResponseDTO;
import com.main.model.entity.Order;
import com.main.model.mapper.OrderMapper;
import com.main.repository.OrderRepository;
import com.main.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class DefaultOrderService extends DefaultGenericService<Order, OrderRequestDTO, OrderResponseDTO> implements OrderService {

    public DefaultOrderService(OrderRepository repository, OrderMapper mapper) {
        super(repository, mapper);
    }
}
