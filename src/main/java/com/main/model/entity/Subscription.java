package com.main.model.entity;

import com.main.infrastructure.generic.model.entity.GenericEntity;
import com.main.model.enums.SubscriptionStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "subscriptions")
@Getter
@Setter
public class Subscription extends GenericEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionStatus status = SubscriptionStatus.PENDING;

    @Column(name = "amount_paid", precision = 10, scale = 2)
    private BigDecimal amountPaid;

    public boolean isExpired() {
        return LocalDate.now().isAfter(this.endDate);
    }

    public boolean hasAccess() {
        LocalDate today = LocalDate.now();
        return SubscriptionStatus.ACTIVE.equals(this.status) &&
                !today.isBefore(startDate) &&
                !today.isAfter(endDate);
    }

    public boolean isClosingToExpire(int days) {
        LocalDate warningDate = LocalDate.now().plusDays(days);
        return !isExpired() && (endDate.isBefore(warningDate) || endDate.isEqual(warningDate));
    }

    public long getDaysRemaining() {
        if (isExpired()) return 0;
        return java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), endDate);
    }
}