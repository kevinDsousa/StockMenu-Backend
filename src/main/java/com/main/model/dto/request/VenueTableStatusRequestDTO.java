package com.main.model.dto.request;

import com.main.model.enums.TableStatus;
import jakarta.validation.constraints.NotNull;

public record VenueTableStatusRequestDTO(
        @NotNull(message = "Status is required")
        TableStatus status
) {
}
