package com.main.model.dto.request;

import com.main.model.enums.OrderType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record OrderRequestDTO(
        @NotNull(message = "Company ID is required")
        UUID companyId,

        UUID tableId,

        @NotNull(message = "Order type is required")
        OrderType type,

        @Size(max = 100)
        String customerName,

        @Size(max = 255)
        String deliveryAddress,

        UUID paymentMethodId,

        @NotEmpty(message = "Order must have at least one item")
        @Valid
        List<OrderItemRequestDTO> items
) {
}