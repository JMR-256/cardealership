package com.training.cardealership.exceptions;

import com.training.cardealership.enums.ExceptionsEnum;

public class InvalidQueryException extends ServiceException {

    public InvalidQueryException(ExceptionsEnum error) {
        this(error, "Query parameter is invalid");;
    }

    public InvalidQueryException(ExceptionsEnum error, String msg) {
        super(error, msg);
    }

}
