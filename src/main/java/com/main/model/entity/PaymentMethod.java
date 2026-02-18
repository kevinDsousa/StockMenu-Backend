package com.main.model.entity;

import com.main.infrastructure.exeptions.BusinessRuleException;
import com.main.infrastructure.generic.model.entity.GenericEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "payment_methods")
@Getter
@Setter
public class PaymentMethod extends GenericEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private boolean active = true;

    @Column(name = "allows_delivery", nullable = false)
    private boolean allowsDelivery = true;

    @Column(name = "is_online_payment", nullable = false)
    private boolean onlinePayment = false;

    public boolean isValidForContext(boolean isDelivery) {
        if (!this.active) return false;
        if (isDelivery && !this.allowsDelivery) return false;
        return true;
    }

    private void validatePaymentName() {
        if (name == null || name.trim().length() < 2) {
            throw new BusinessRuleException("Payment method name must have at least 2 characters.");
        }
    }
}