package com.main.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CompanyRequestDTO(
        @NotBlank(message = "Trade name is required")
        @Size(max = 150)
        String tradeName,

        @Size(max = 150)
        String corporateName,

        @NotBlank(message = "CNPJ is required")
        @Pattern(regexp = "\\d{14}", message = "CNPJ must have 14 digits")
        String cnpj,

        @Size(max = 20)
        @Pattern(
                regexp = "^\\(\\d{2}\\)\\d{5}-\\d{4}$",
                message = "Whatsapp must follow the pattern (85)99928-7198"
        )
        String whatsapp
) {
}