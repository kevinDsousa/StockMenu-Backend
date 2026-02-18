package com.main.model.enums;

import com.main.infrastructure.generic.model.enums.GenericEnum;

public enum SubscriptionStatus implements GenericEnum {

    ACTIVE("ACTIVE", "Ativo"),
    INACTIVE("INACTIVE", "Inativo"),
    PENDING("PENDING", "Pendente");

    private final String key;
    private final String value;

    SubscriptionStatus(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }
}
