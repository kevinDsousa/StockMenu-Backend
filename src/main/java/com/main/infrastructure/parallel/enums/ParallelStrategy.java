package com.main.infrastructure.parallel.enums;

import com.main.infrastructure.generic.model.enums.GenericEnum;

public enum ParallelStrategy implements GenericEnum {

    FAIL_FAST("FAIL_FAST", "Falha Rápida"),
    BEST_EFFORT("BEST_EFFORT", "Melhor Esforço");

    private final String key;
    private final String value;

    ParallelStrategy(String key, String value) {
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
