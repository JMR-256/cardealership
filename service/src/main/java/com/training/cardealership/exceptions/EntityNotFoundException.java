package com.training.cardealership.exceptions;

import com.training.cardealership.enums.ExceptionsEnum;
import org.springframework.dao.DataRetrievalFailureException;

public class EntityNotFoundException extends ServiceException {

    public EntityNotFoundException(ExceptionsEnum error) {
        this(error, "Entity not found");
    }

    public EntityNotFoundException(ExceptionsEnum error, String msg) {
        super(error, msg);
    }

}
