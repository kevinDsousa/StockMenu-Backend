package com.main.model.entity;

import com.main.infrastructure.generic.model.entity.GenericEntity;
import com.main.model.enums.UnitMeasure;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "primary_products")
@Getter
@Setter
public class PrimaryProduct extends GenericEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "current_stock", precision = 12, scale = 3, nullable = false)
    private BigDecimal currentStock = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UnitMeasure unit;

    @Column(name = "low_stock_alert", precision = 12, scale = 3)
    private BigDecimal lowStockAlert;

    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    @Column(name = "product_type")
    private String productType;

    public boolean isStockLow() {
        return lowStockAlert != null && currentStock.compareTo(lowStockAlert) <= 0;
    }

    public boolean isExpired() {
        return LocalDate.now().isAfter(this.expirationDate);
    }

    public boolean isExpiringSoon(int daysThreshold) {
        LocalDate limitDate = LocalDate.now().plusDays(daysThreshold);
        return !isExpired() && (expirationDate.isBefore(limitDate) || expirationDate.isEqual(limitDate));
    }
}