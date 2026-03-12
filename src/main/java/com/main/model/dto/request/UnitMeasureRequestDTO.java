package com.main.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UnitMeasureRequestDTO(
        @NotBlank(message = "Código é obrigatório")
        @Size(max = 20)
        String key,

        @NotBlank(message = "Descrição é obrigatória")
        @Size(max = 100)
        String label,

        boolean active
) {
}
