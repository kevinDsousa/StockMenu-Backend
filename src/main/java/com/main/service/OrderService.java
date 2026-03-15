package com.main.service;

import com.main.infrastructure.generic.model.dto.PagedResponseDTO;
import com.main.infrastructure.generic.service.GenericService;
import com.main.model.dto.request.OrderRequestDTO;
import com.main.model.dto.response.OrderResponseDTO;

import java.util.List;
import java.util.UUID;

public interface OrderService extends GenericService<OrderRequestDTO, OrderResponseDTO> {

    List<OrderResponseDTO> findByCompanyId(UUID companyId);

    PagedResponseDTO<OrderResponseDTO> findPageByCompanyId(UUID companyId, int page, int size,
                                                            String tableSearch, String customerSearch, String itemSearch);

    OrderResponseDTO transferOrderToTable(UUID orderId, UUID targetTableId);

    void closeOrdersByIds(com.main.model.dto.request.OrderCloseBatchRequestDTO request);
}
