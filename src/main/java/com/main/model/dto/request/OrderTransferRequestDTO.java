package com.main.model.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record OrderTransferRequestDTO(
        @NotNull(message = "Order ID is required")
        UUID orderId,

        @NotNull(message = "Target table ID is required")
        UUID targetTableId
) {
}
