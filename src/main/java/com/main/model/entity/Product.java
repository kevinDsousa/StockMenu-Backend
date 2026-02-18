package com.main.model.entity;

import com.main.infrastructure.generic.model.entity.GenericEntity;
import com.main.model.enums.UnitMeasure;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product extends GenericEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "primary_product_id", nullable = false)
    private PrimaryProduct primaryProduct;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "sell_unit", nullable = false)
    private UnitMeasure sellUnit;

    @Column(name = "is_fractional", nullable = false)
    private boolean fractional = false;

    @Column(name = "custom_expiration_date")
    private LocalDate customExpirationDate;

    @Column(nullable = false)
    private boolean active = true;

    public LocalDate getEffectiveExpirationDate() {
        if (this.customExpirationDate != null) {
            return this.customExpirationDate;
        }
        return (primaryProduct != null) ? primaryProduct.getExpirationDate() : null;
    }

    public boolean isExpired() {
        LocalDate effectiveDate = getEffectiveExpirationDate();
        return effectiveDate != null && LocalDate.now().isAfter(effectiveDate);
    }
}