package com.main.model.dto.request;

import com.main.model.enums.OrderItemStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemRequestDTO(
        UUID orderId,

        UUID productId,

        @Positive(message = "Quantity must be greater than zero")
        BigDecimal quantity,

        @Positive(message = "Unit price must be positive when provided")
        BigDecimal unitPrice,

        @Size(max = 50, message = "Customer name is too long")
        String customerName,

        @Size(max = 255, message = "Observation is too long")
        String observation,

        OrderItemStatus status
) {
}