package com.main.model.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record VenueTableSplitRequestDTO(
        @NotEmpty(message = "At least one target table with orders is required")
        @Valid
        List<SplitTargetDTO> targets
) {
    public record SplitTargetDTO(
            @NotNull(message = "Table number is required")
            Integer tableNumber,

            Integer capacity,

            @NotEmpty(message = "At least one order must be assigned to this table")
            List<UUID> orderIds
    ) {}
}
