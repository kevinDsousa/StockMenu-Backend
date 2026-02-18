package com.main.model.dto.response;

import com.main.infrastructure.generic.model.dto.GenericDTO;
import com.main.model.enums.UnitMeasure;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class ProductResponseDTO extends GenericDTO {

    private UUID primaryProductId;
    private String primaryProductName;
    private String name;
    private BigDecimal price;
    private UnitMeasure sellUnit;
    private boolean fractional;
    private boolean active;

    private LocalDate effectiveExpirationDate;
    private boolean isExpired;
}