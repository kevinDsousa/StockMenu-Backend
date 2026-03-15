package com.main.service.impl;

import com.main.infrastructure.exeptions.BusinessRuleException;
import com.main.infrastructure.generic.model.dto.PagedResponseDTO;
import com.main.infrastructure.generic.service.impl.DefaultGenericService;
import com.main.infrastructure.security.AuthorizationService;
import com.main.model.dto.request.OrderRequestDTO;
import com.main.model.dto.response.OrderResponseDTO;
import com.main.model.dto.request.OrderCloseBatchRequestDTO;
import com.main.model.entity.Company;
import com.main.model.entity.Order;
import com.main.model.entity.User;
import com.main.model.entity.VenueTable;
import com.main.model.enums.TableStatus;
import com.main.model.mapper.OrderMapper;
import com.main.repository.CompanyRepository;
import com.main.repository.OrderRepository;
import com.main.repository.UserRepository;
import com.main.repository.VenueTableRepository;
import com.main.service.OrderService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class DefaultOrderService extends DefaultGenericService<Order, OrderRequestDTO, OrderResponseDTO> implements OrderService {

    private final CompanyRepository companyRepository;
    private final VenueTableRepository venueTableRepository;
    private final UserRepository userRepository;
    private final AuthorizationService authorizationService;

    public DefaultOrderService(OrderRepository repository,
                               OrderMapper mapper,
                               CompanyRepository companyRepository,
                               VenueTableRepository venueTableRepository,
                               UserRepository userRepository,
                               AuthorizationService authorizationService) {
        super(repository, mapper);
        this.companyRepository = companyRepository;
        this.venueTableRepository = venueTableRepository;
        this.userRepository = userRepository;
        this.authorizationService = authorizationService;
    }

    @Override
    @Transactional
    public OrderResponseDTO create(OrderRequestDTO request) {
        authorizationService.requireCompanyAccess(request.companyId());
        Company company = companyRepository.findById(request.companyId())
                .orElseThrow(() -> new BusinessRuleException("Empresa não encontrada"));

        Order entity = new Order();
        entity.setCompany(company);
        entity.setType(request.type());
        entity.setCustomerName(request.customerName());
        entity.setDeliveryAddress(request.deliveryAddress());
        entity.setInvoiced(false);

        User currentUser = userRepository.findById(authorizationService.getCurrentPrincipal().userId())
                .orElse(null);
        if (currentUser != null) {
            entity.setCreatedByUser(currentUser);
        }

        if (request.tableId() != null) {
            VenueTable table = venueTableRepository.findById(request.tableId())
                    .orElseThrow(() -> new BusinessRuleException("Mesa não encontrada"));
            if (!table.getCompany().getId().equals(company.getId())) {
                throw new BusinessRuleException("Mesa não pertence à empresa");
            }
            entity.setVenueTable(table);
            if (TableStatus.FREE.equals(table.getStatus())) {
                table.setStatus(TableStatus.OCCUPIED);
                venueTableRepository.save(table);
            }
        }

        entity.setPaymentMethod(null);
        entity.setTotalAmount(BigDecimal.ZERO);

        Order saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDTO> findByCompanyId(UUID companyId) {
        authorizationService.requireCompanyAccess(companyId);
        OrderRepository orderRepository = (OrderRepository) repository;
        return mapper.toResponseList(orderRepository.findByCompany_IdWithVenueTable(companyId));
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponseDTO<OrderResponseDTO> findPageByCompanyId(UUID companyId, int page, int size,
                                                                  String tableSearch, String customerSearch, String itemSearch) {
        authorizationService.requireCompanyAccess(companyId);
        OrderRepository orderRepository = (OrderRepository) repository;
        String t = (tableSearch != null && !tableSearch.isBlank()) ? tableSearch.trim() : null;
        String c = (customerSearch != null && !customerSearch.isBlank()) ? customerSearch.trim() : null;
        String i = (itemSearch != null && !itemSearch.isBlank()) ? itemSearch.trim() : null;
        Pageable pageable = PageRequest.of(page, size);
        var springPage = orderRepository.findByCompanyIdWithSearch(companyId, t, c, i, pageable);
        return PagedResponseDTO.<OrderResponseDTO>builder()
                .content(mapper.toResponseList(springPage.getContent()))
                .totalElements(springPage.getTotalElements())
                .totalPages(springPage.getTotalPages())
                .size(springPage.getSize())
                .number(springPage.getNumber())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDTO findById(UUID id) {
        OrderRepository orderRepository = (OrderRepository) repository;
        Order order = orderRepository.findByIdWithVenueTable(id)
                .orElseThrow(() -> new BusinessRuleException("Registro não encontrado"));
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

    @Override
    @Transactional
    public void closeOrdersByIds(OrderCloseBatchRequestDTO request) {
        for (UUID id : request.orderIds()) {
            Order order = repository.findById(id)
                    .orElseThrow(() -> new BusinessRuleException("Pedido não encontrado: " + id));
            authorizationService.requireCompanyAccess(order.getCompany().getId());
            if (!order.canBeModified()) {
                throw new BusinessRuleException("Pedido não pode ser alterado (já faturado ou excluído): " + id);
            }
            order.setInvoiced(true);
            repository.save(order);
        }
        if (request.tableId() != null) {
            VenueTable table = venueTableRepository.findById(request.tableId())
                    .orElseThrow(() -> new BusinessRuleException("Mesa não encontrada"));
            authorizationService.requireCompanyAccess(table.getCompany().getId());
            table.setStatus(TableStatus.WAITING_FOR_BILL);
            venueTableRepository.save(table);
        }
    }
}
