package com.training.cardealership.exceptions;

import com.training.cardealership.enums.ExceptionsEnum;

public class ServiceException extends RuntimeException {
    protected ExceptionsEnum error;

    public ServiceException(ExceptionsEnum errorEnum) {
        this(errorEnum, "Generic error");
    }

    public ServiceException(ExceptionsEnum errorEnum, String message) {
        super(message);
        this.error = errorEnum;
    }

    public ExceptionsEnum getErrorEnum() {
        return error;
    }
}
