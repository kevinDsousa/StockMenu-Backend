package com.main.model.dto.request;

import com.main.model.enums.UnitMeasure;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PrimaryProductRequestDTO(
        @NotNull(message = "Company ID is required")
        UUID companyId,

        @NotBlank(message = "Primary product name is required")
        String name,

        @NotNull(message = "Current stock is required")
        @PositiveOrZero(message = "Stock cannot be negative")
        BigDecimal currentStock,

        @NotNull(message = "Unit of measure is required")
        UnitMeasure unit,

        @PositiveOrZero(message = "Low stock alert must be positive or zero")
        BigDecimal lowStockAlert,

        @NotNull(message = "Expiration date is required")
        @FutureOrPresent(message = "Expiration date must be today or in the future")
        LocalDate expirationDate,

        String productType
) {
}