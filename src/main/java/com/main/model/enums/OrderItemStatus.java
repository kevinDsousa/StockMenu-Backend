package com.main.model.enums;

import com.main.infrastructure.generic.model.enums.GenericEnum;

public enum OrderItemStatus implements GenericEnum {

    PENDING("PENDING", "Pendente"),
    IN_PREPARATION("IN_PREPARATION", "Em preparo"),
    DELIVERED("DELIVERED", "Entregue"),
    CANCELLED("CANCELLED", "Cancelado");

    private final String key;
    private final String value;

    OrderItemStatus(String key, String value) {
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
