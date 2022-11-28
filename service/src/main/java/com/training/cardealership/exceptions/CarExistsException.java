package com.training.cardealership.exceptions;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CarExistsException extends DuplicateKeyException {

    public CarExistsException() {
        super("Car already exists");
    }

    public CarExistsException(String msg) {
        super(msg);
    }

    public CarExistsException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
