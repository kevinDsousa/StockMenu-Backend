package com.main.service.impl;

import com.main.infrastructure.exeptions.BusinessRuleException;
import com.main.infrastructure.generic.service.impl.DefaultGenericService;
import com.main.infrastructure.security.AuthorizationService;
import com.main.model.dto.request.OrderItemRequestDTO;
import com.main.model.dto.response.OrderItemResponseDTO;
import com.main.model.entity.Order;
import com.main.model.entity.OrderItem;
import com.main.model.entity.Product;
import com.main.model.enums.OrderItemStatus;
import com.main.model.mapper.OrderItemMapper;
import com.main.repository.OrderItemRepository;
import com.main.repository.OrderRepository;
import com.main.repository.ProductRepository;
import com.main.service.OrderItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class DefaultOrderItemService extends DefaultGenericService<OrderItem, OrderItemRequestDTO, OrderItemResponseDTO> implements OrderItemService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final AuthorizationService authorizationService;

    public DefaultOrderItemService(OrderItemRepository repository, OrderItemMapper mapper, OrderRepository orderRepository, ProductRepository productRepository, AuthorizationService authorizationService) {
        super(repository, mapper);
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.authorizationService = authorizationService;
    }

    @Override
    @Transactional
    public OrderItemResponseDTO create(OrderItemRequestDTO request) {
        if (request.orderId() == null || request.productId() == null) {
            throw new BusinessRuleException("Order ID e Product ID são obrigatórios");
        }
        Order order = orderRepository.findById(request.orderId())
                .orElseThrow(() -> new BusinessRuleException("Pedido não encontrado"));
        authorizationService.requireCompanyAccess(order.getCompany().getId());
        if (!order.canBeModified()) {
            throw new BusinessRuleException("Pedido não pode ser alterado (já faturado ou excluído)");
        }
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new BusinessRuleException("Produto não encontrado"));
        if (!product.getCompany().getId().equals(order.getCompany().getId())) {
            throw new BusinessRuleException("Produto não pertence à empresa do pedido");
        }
        BigDecimal unitPrice = request.unitPrice() != null ? request.unitPrice() : product.getPrice();

        OrderItem item = new OrderItem();
        item.setOrder(order);
        item.setProduct(product);
        item.setQuantity(request.quantity());
        item.setUnitPrice(unitPrice);
        item.setCustomerName(request.customerName());
        item.setObservation(request.observation());
        item.setStatus(OrderItemStatus.PENDING);
        OrderItem saved = repository.save(item);
        order.calculateTotal();
        orderRepository.save(order);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional
    public OrderItemResponseDTO update(UUID id, OrderItemRequestDTO request) {
        OrderItem item = repository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("Item do pedido não encontrado"));
        authorizationService.requireCompanyAccess(item.getOrder().getCompany().getId());
        if (!item.getOrder().canBeModified()) {
            throw new BusinessRuleException("Pedido não pode ser alterado (já faturado ou excluído)");
        }
        if (request.status() != null) {
            item.setStatus(request.status());
        }
        if (request.quantity() != null) {
            item.setQuantity(request.quantity());
        }
        if (request.unitPrice() != null) {
            item.setUnitPrice(request.unitPrice());
        }
        if (request.customerName() != null) {
            item.setCustomerName(request.customerName());
        }
        if (request.observation() != null) {
            item.setObservation(request.observation());
        }
        OrderItem saved = repository.save(item);
        item.getOrder().calculateTotal();
        orderRepository.save(item.getOrder());
        return mapper.toResponse(saved);
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
        return mapper.toResponseList(orderItemRepository.findByOrder_IdWithProduct(orderId));
    }
}
