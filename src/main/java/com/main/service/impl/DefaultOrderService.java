package com.main.service.impl;

import com.main.infrastructure.exeptions.BusinessRuleException;
import com.main.infrastructure.generic.service.impl.DefaultGenericService;
import com.main.infrastructure.security.AuthorizationService;
import com.main.model.dto.request.OrderRequestDTO;
import com.main.model.dto.response.OrderResponseDTO;
import com.main.model.entity.Order;
import com.main.model.entity.VenueTable;
import com.main.model.mapper.OrderMapper;
import com.main.repository.OrderRepository;
import com.main.repository.VenueTableRepository;
import com.main.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class DefaultOrderService extends DefaultGenericService<Order, OrderRequestDTO, OrderResponseDTO> implements OrderService {

    private final VenueTableRepository venueTableRepository;
    private final AuthorizationService authorizationService;

    public DefaultOrderService(OrderRepository repository, OrderMapper mapper, VenueTableRepository venueTableRepository, AuthorizationService authorizationService) {
        super(repository, mapper);
        this.venueTableRepository = venueTableRepository;
        this.authorizationService = authorizationService;
    }

    @Override
    public List<OrderResponseDTO> findByCompanyId(UUID companyId) {
        authorizationService.requireCompanyAccess(companyId);
        return mapper.toResponseList(((OrderRepository) repository).findByCompany_Id(companyId));
    }

    @Override
    public OrderResponseDTO findById(UUID id) {
        Order order = repository.findById(id).orElseThrow(() -> new BusinessRuleException("Registro não encontrado"));
        authorizationService.requireCompanyAccess(order.getCompany().getId());
        return mapper.toResponse(order);
    }

    @Override
    public void delete(UUID id) {
        Order order = repository.findById(id).orElseThrow(() -> new BusinessRuleException("Registro não encontrado"));
        authorizationService.requireCompanyAccess(order.getCompany().getId());
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public OrderResponseDTO transferOrderToTable(UUID orderId, UUID targetTableId) {
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new BusinessRuleException("Pedido não encontrado"));
        authorizationService.requireCompanyAccess(order.getCompany().getId());
        if (!order.canBeModified()) {
            throw new BusinessRuleException("Pedido não pode ser alterado (já faturado ou excluído)");
        }
        VenueTable targetTable = venueTableRepository.findById(targetTableId)
                .orElseThrow(() -> new BusinessRuleException("Mesa de destino não encontrada"));
        if (!order.getCompany().getId().equals(targetTable.getCompany().getId())) {
            throw new BusinessRuleException("Mesa de destino não pertence à mesma empresa");
        }
        order.setVenueTable(targetTable);
        order.calculateTotal();
        return mapper.toResponse(repository.save(order));
    }
}
