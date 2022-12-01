package com.training.cardealership.enums;

public enum ExceptionsEnum {

    DELETE_ERROR("Incorrect id provided"),
    GET_ERROR("Incorrect query parameter provided"),
    INVALID_CAR("Incorrect car data provided");

    private final String description;

    ExceptionsEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
