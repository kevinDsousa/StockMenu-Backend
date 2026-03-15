package com.main.service.impl;

import com.main.infrastructure.exeptions.BusinessRuleException;
import com.main.infrastructure.generic.service.impl.DefaultGenericService;
import com.main.infrastructure.security.AuthorizationService;
import com.main.model.dto.request.OrderItemRequestDTO;
import com.main.model.dto.response.OrderItemResponseDTO;
import com.main.model.entity.Order;
import com.main.model.entity.OrderItem;
import com.main.model.enums.OrderItemStatus;
import com.main.model.mapper.OrderItemMapper;
import com.main.repository.OrderItemRepository;
import com.main.repository.OrderRepository;
import com.main.service.OrderItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class DefaultOrderItemService extends DefaultGenericService<OrderItem, OrderItemRequestDTO, OrderItemResponseDTO> implements OrderItemService {

    private final OrderRepository orderRepository;
    private final AuthorizationService authorizationService;

    public DefaultOrderItemService(OrderItemRepository repository, OrderItemMapper mapper, OrderRepository orderRepository, AuthorizationService authorizationService) {
        super(repository, mapper);
        this.orderRepository = orderRepository;
        this.authorizationService = authorizationService;
    }

    @Override
    @Transactional
    public OrderItemResponseDTO cancel(UUID orderItemId) {
        OrderItem item = repository.findById(orderItemId)
                .orElseThrow(() -> new BusinessRuleException("Item do pedido não encontrado"));
        if (item.getOrder() == null) {
            throw new BusinessRuleException("Item do pedido não está vinculado a um pedido");
        }
        authorizationService.requireCompanyAccess(item.getOrder().getCompany().getId());
        if (!item.getOrder().canBeModified()) {
            throw new BusinessRuleException("Pedido não pode ser alterado (já faturado ou excluído)");
        }
        item.setStatus(OrderItemStatus.CANCELLED);
        repository.save(item);
        item.getOrder().calculateTotal();
        orderRepository.save(item.getOrder());
        return mapper.toResponse(repository.findById(orderItemId).orElse(item));
    }

    @Override
    public List<OrderItemResponseDTO> findByOrderId(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessRuleException("Pedido não encontrado"));
        authorizationService.requireCompanyAccess(order.getCompany().getId());
        OrderItemRepository orderItemRepository = (OrderItemRepository) repository;
        return mapper.toResponseList(orderItemRepository.findByOrder_Id(orderId));
    }
}
