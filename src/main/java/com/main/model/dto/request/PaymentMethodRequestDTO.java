package com.main.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record PaymentMethodRequestDTO(
        @NotNull(message = "O ID da empresa é obrigatório.")
        UUID companyId,

        @NotBlank(message = "O nome do método de pagamento é obrigatório.")
        @Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 50 caracteres.")
        String name,

        @NotNull(message = "Defina se o método está ativo.")
        Boolean active,

        @NotNull(message = "Defina se permite entrega.")
        Boolean allowsDelivery,

        @NotNull(message = "Defina se é pagamento online.")
        Boolean onlinePayment
) {
}