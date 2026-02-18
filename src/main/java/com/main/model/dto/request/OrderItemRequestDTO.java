package com.main.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemRequestDTO(
        @NotNull(message = "Product ID is required")
        UUID productId,

        @NotNull(message = "Quantity is required")
        @Positive(message = "Quantity must be greater than zero")
        BigDecimal quantity,

        @Size(max = 50, message = "Customer name is too long")
        String customerName,

        @Size(max = 255, message = "Observation is too long")
        String observation
) {
}