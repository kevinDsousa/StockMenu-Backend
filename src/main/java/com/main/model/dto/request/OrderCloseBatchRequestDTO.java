package com.main.model.dto.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.UUID;

public record OrderCloseBatchRequestDTO(
        @NotEmpty(message = "At least one order ID is required")
        List<UUID> orderIds,

        UUID tableId
) {
}
