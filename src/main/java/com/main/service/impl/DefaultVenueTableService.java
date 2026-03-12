package com.main.service.impl;

import com.main.infrastructure.exeptions.BusinessRuleException;
import com.main.infrastructure.generic.service.impl.DefaultGenericService;
import com.main.model.dto.request.VenueTableMergeRequestDTO;
import com.main.model.dto.request.VenueTableRequestDTO;
import com.main.model.dto.request.VenueTableSplitRequestDTO;
import com.main.model.dto.response.VenueTableResponseDTO;
import com.main.model.entity.Order;
import com.main.model.entity.VenueTable;
import com.main.model.enums.TableStatus;
import com.main.model.mapper.VenueTableMapper;
import com.main.repository.OrderRepository;
import com.main.repository.VenueTableRepository;
import com.main.service.VenueTableService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class DefaultVenueTableService extends DefaultGenericService<VenueTable, VenueTableRequestDTO, VenueTableResponseDTO> implements VenueTableService {

    private final OrderRepository orderRepository;

    public DefaultVenueTableService(VenueTableRepository repository, VenueTableMapper mapper, OrderRepository orderRepository) {
        super(repository, mapper);
        this.orderRepository = orderRepository;
    }

    @Override
    public List<VenueTableResponseDTO> findByCompanyId(UUID companyId) {
        VenueTableRepository venueTableRepository = (VenueTableRepository) repository;
        return mapper.toResponseList(venueTableRepository.findByCompany_Id(companyId));
    }

    @Override
    @Transactional
    public VenueTableResponseDTO split(UUID tableId, VenueTableSplitRequestDTO request) {
        VenueTable sourceTable = repository.findById(tableId)
                .orElseThrow(() -> new BusinessRuleException("Mesa não encontrada"));
        for (VenueTableSplitRequestDTO.SplitTargetDTO target : request.targets()) {
            VenueTable newTable = new VenueTable();
            newTable.setCompany(sourceTable.getCompany());
            newTable.setTableNumber(target.tableNumber());
            newTable.setCapacity(target.capacity());
            newTable.setStatus(TableStatus.OCCUPIED);
            newTable.setActive(true);
            newTable = repository.save(newTable);
            for (UUID orderId : target.orderIds()) {
                Order order = orderRepository.findById(orderId)
                        .orElseThrow(() -> new BusinessRuleException("Pedido não encontrado: " + orderId));
                if (order.getVenueTable() == null || !order.getVenueTable().getId().equals(tableId)) {
                    throw new BusinessRuleException("Pedido não pertence à mesa de origem");
                }
                if (order.isInvoiced()) {
                    throw new BusinessRuleException("Não é possível mover pedido já faturado");
                }
                order.setVenueTable(newTable);
                orderRepository.save(order);
            }
        }
        List<Order> remaining = orderRepository.findByVenueTable_Id(tableId);
        if (remaining.isEmpty()) {
            sourceTable.setStatus(TableStatus.FREE);
            repository.save(sourceTable);
        }
        return mapper.toResponse(repository.findById(tableId).orElse(sourceTable));
    }

    @Override
    @Transactional
    public VenueTableResponseDTO merge(VenueTableMergeRequestDTO request) {
        VenueTable targetTable;
        if (request.targetTableId() != null) {
            targetTable = repository.findById(request.targetTableId())
                    .orElseThrow(() -> new BusinessRuleException("Mesa de destino não encontrada"));
            if (!targetTable.getCompany().getId().equals(request.companyId())) {
                throw new BusinessRuleException("Mesa de destino não pertence à empresa");
            }
        } else {
            VenueTable firstSource = repository.findById(request.sourceTableIds().get(0))
                    .orElseThrow(() -> new BusinessRuleException("Mesa de origem não encontrada"));
            targetTable = new VenueTable();
            targetTable.setCompany(firstSource.getCompany());
            targetTable.setTableNumber(0);
            targetTable.setStatus(TableStatus.OCCUPIED);
            targetTable.setActive(true);
            targetTable = repository.save(targetTable);
        }
        for (UUID sourceTableId : request.sourceTableIds()) {
            if (sourceTableId.equals(targetTable.getId())) continue;
            VenueTable source = repository.findById(sourceTableId)
                    .orElseThrow(() -> new BusinessRuleException("Mesa de origem não encontrada"));
            if (!source.getCompany().getId().equals(request.companyId())) {
                throw new BusinessRuleException("Mesa não pertence à empresa");
            }
            List<Order> orders = orderRepository.findByVenueTable_Id(sourceTableId);
            for (Order order : orders) {
                if (!order.isInvoiced()) {
                    order.setVenueTable(targetTable);
                    orderRepository.save(order);
                }
            }
            source.setStatus(TableStatus.FREE);
            repository.save(source);
        }
        return mapper.toResponse(repository.findById(targetTable.getId()).orElse(targetTable));
    }
}
