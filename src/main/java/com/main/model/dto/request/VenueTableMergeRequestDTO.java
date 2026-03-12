package com.main.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record VenueTableMergeRequestDTO(
        @NotNull(message = "Company ID is required")
        UUID companyId,

        @NotEmpty(message = "At least one source table is required")
        List<UUID> sourceTableIds,

        UUID targetTableId
) {
}
