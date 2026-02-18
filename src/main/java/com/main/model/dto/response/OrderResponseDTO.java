package com.main.model.dto.response;

import com.main.infrastructure.generic.model.dto.GenericDTO;
import com.main.model.enums.OrderType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class OrderResponseDTO extends GenericDTO {

    private UUID companyId;
    private UUID tableId;
    private Integer tableNumber;
    private OrderType type;
    private boolean invoiced;
    private String customerName;
    private String deliveryAddress;
    private BigDecimal totalAmount;
    private List<OrderItemResponseDTO> items;

    public boolean isPending() {
        return !invoiced;
    }
}