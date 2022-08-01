package com.alexlin7.demo.aop;

public enum EntityType {
    PRODUCT("product"), APP_USER("user");

    private final String presentName;

    EntityType(String presentName) {
        this.presentName = presentName;
    }

    @Override
    public String toString() {
        return presentName;
    }
}
