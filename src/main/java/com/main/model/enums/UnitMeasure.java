package com.main.model.enums;

import com.main.infrastructure.generic.model.enums.GenericEnum;

public enum UnitMeasure implements GenericEnum {

    KG("KG", "Kg"),
    G("G", "Grama"),
    L("L", "Litro"),
    ML("ML", "Mililitro"),
    UN("UN", "Unidade");

    private final String key;
    private final String value;

    UnitMeasure(String key, String value) {
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
