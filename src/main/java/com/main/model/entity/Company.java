package com.main.model.entity;

import com.main.infrastructure.generic.model.entity.GenericEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "companies")
@Getter
@Setter
public class Company extends GenericEntity {

    @Column(name = "trade_name", nullable = false, length = 150)
    private String tradeName;

    @Column(name = "corporate_name", length = 150)
    private String corporateName;

    @Column(name = "cnpj", unique = true, length = 14)
    private String cnpj;

    @Column(name = "whatsapp", length = 20)
    private String whatsapp;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Subscription> subscriptions;

    public Subscription getActiveSubscription() {
        if (subscriptions == null) return null;
        return subscriptions.stream()
                .filter(Subscription::hasAccess)
                .findFirst()
                .orElse(null);
    }

    public boolean canOperate() {
        return this.active && getActiveSubscription() != null;
    }
}