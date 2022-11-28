package com.training.cardealership;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({HttpMessageNotReadableException.class, ConstraintViolationException.class})
    ResponseEntity<Map<String, String>> malformedRequestHandler(){
        return new ResponseEntity<>(Map.of("description","Incorrect car data provided"), HttpStatus.BAD_REQUEST);
    }
}
