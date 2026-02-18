package com.main.model.dto.request;

import com.main.model.enums.UnitMeasure;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ProductRequestDTO(
        @NotNull(message = "Company ID is required")
        UUID companyId,

        @NotNull(message = "Primary product ID is required")
        UUID primaryProductId,

        @NotBlank(message = "Product name is required")
        String name,

        @NotNull(message = "Price is required")
        @Positive(message = "Price must be greater than zero")
        BigDecimal price,

        @NotNull(message = "Sell unit is required")
        UnitMeasure sellUnit,

        boolean fractional,

        LocalDate customExpirationDate,

        boolean active
) {
}