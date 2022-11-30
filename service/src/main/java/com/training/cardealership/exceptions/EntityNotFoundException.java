package com.training.cardealership.exceptions;

import org.springframework.dao.DataRetrievalFailureException;

public class EntityNotFoundException extends DataRetrievalFailureException {

    public EntityNotFoundException() {
        this("The entity could not be found");
    }
    public EntityNotFoundException(String msg) {
        super(msg);
    }

    public EntityNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
