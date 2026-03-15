package com.main.model.dto.response;

import com.main.infrastructure.generic.model.dto.GenericDTO;
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
    private LocalDate primaryProductStockEntryDate;
    private String primaryProductStorageType;
    private Integer primaryProductMaxStorageDays;
    private String name;
    private BigDecimal price;
    private String sellUnit;
    private boolean fractional;
    private boolean active;

    private LocalDate effectiveExpirationDate;
    private boolean isExpired;
    private boolean stockLow;
    private String imageUrl;
}