package com.main.model.dto.request;

import com.main.model.enums.SubscriptionStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record SubscriptionRequestDTO(
        @NotNull(message = "O ID da empresa é obrigatório.")
        UUID companyId,

        @NotNull(message = "A data de início é obrigatória.")
        LocalDate startDate,

        @NotNull(message = "A data de término é obrigatória.")
        @FutureOrPresent(message = "A data de expiração deve ser hoje ou uma data futura.")
        LocalDate endDate,

        @NotNull(message = "O status da assinatura é obrigatório.")
        SubscriptionStatus status,

        @NotNull(message = "O valor pago é obrigatório.")
        @PositiveOrZero(message = "O valor deve ser maior ou igual a zero.")
        BigDecimal amountPaid
) {
}