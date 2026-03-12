package com.main.model.enums;

import com.main.infrastructure.generic.model.enums.GenericEnum;

public enum UserRole implements GenericEnum {

    SUPER_ADMIN("SUPER_ADMIN", "Super administrador"),
    COMPANY_ADMIN("COMPANY_ADMIN", "Administrador da empresa"),
    WAITER("WAITER", "Garçom");

    private final String key;
    private final String value;

    UserRole(String key, String value) {
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
