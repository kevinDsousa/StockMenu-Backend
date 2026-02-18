package com.main.infrastructure.generic.model.enums;

public interface GenericEnum {

    String getKey();
    String getValue();

    default String toCapitalized() {
        String name = this.toString().toLowerCase();
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
