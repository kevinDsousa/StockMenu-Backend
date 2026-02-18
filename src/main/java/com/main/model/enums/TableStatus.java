package com.main.model.enums;

import com.main.infrastructure.generic.model.enums.GenericEnum;

public enum TableStatus implements GenericEnum {

    FREE("FREE", "Livre"),
    OCCUPIED("OCCUPIED", "Ocupado"),
    CALLING_WAITER("CALLING_WAITER", "Chamando garçom"),
    WAITING_FOR_BILL("WAITING_FOR_BILL", "Aguardando conta");

    private final String key;
    private final String value;

    TableStatus(String key, String value) {
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
