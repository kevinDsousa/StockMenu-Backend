package com.main.model.enums;

import com.main.infrastructure.generic.model.enums.GenericEnum;

public enum OrderType implements GenericEnum {

    TABLE("TABLE", "Mesa"),
    FREE_ORDER("FREE_ORDER", "Pedido Livre"),
    DELIVERY("DELIVERY", "Delivery");

    private final String key;
    private final String value;

    OrderType(String key, String value) {
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
