package com.main.service.impl;

import com.main.infrastructure.generic.service.impl.DefaultGenericService;
import com.main.model.dto.request.OrderItemRequestDTO;
import com.main.model.dto.response.OrderItemResponseDTO;
import com.main.model.entity.OrderItem;
import com.main.model.mapper.OrderItemMapper;
import com.main.repository.OrderItemRepository;
import com.main.service.OrderItemService;
import org.springframework.stereotype.Service;

@Service
public class DefaultOrderItemService extends DefaultGenericService<OrderItem, OrderItemRequestDTO, OrderItemResponseDTO> implements OrderItemService {

    public DefaultOrderItemService(OrderItemRepository repository, OrderItemMapper mapper)  {
        super(repository, mapper);
    }
}
