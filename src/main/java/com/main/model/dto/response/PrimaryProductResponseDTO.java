package com.main.model.dto.response;

import com.main.infrastructure.generic.model.dto.GenericDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PrimaryProductResponseDTO extends GenericDTO {

    private String name;
    private BigDecimal currentStock;
    private String unit;
    private BigDecimal lowStockAlert;
    private LocalDate expirationDate;
    private String productType;
    private LocalDate stockEntryDate;
    private String storageType;
    private Integer maxStorageDays;

    private boolean isStockLow;
    private boolean isExpired;
    private boolean isExpiringSoon;
    private String imageUrl;
}