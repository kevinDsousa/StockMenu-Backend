package com.main.model.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record VenueTableRequestDTO(
        @NotNull(message = "Company ID is required")
        UUID companyId,

        @NotNull(message = "Table number is required")
        @Min(value = 1, message = "Table number must be at least 1")
        Integer tableNumber,

        @Min(value = 1, message = "Capacity must be at least 1")
        Integer capacity
) {
}
