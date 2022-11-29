package com.training.cardealership.exceptions;

public class InvalidQueryException extends RuntimeException{
    public InvalidQueryException() {
        super("Query was invalid");
    }

    public InvalidQueryException(String queryParam) {
        super(queryParam + " is not a valid query parameter");
    }
}
