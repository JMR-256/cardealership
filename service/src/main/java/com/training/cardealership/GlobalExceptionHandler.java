package com.training.cardealership;

import com.training.cardealership.exceptions.CarExistsException;
import com.training.cardealership.exceptions.EntityNotFoundException;
import com.training.cardealership.exceptions.InvalidQueryException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({HttpMessageNotReadableException.class, ConstraintViolationException.class, EntityNotFoundException.class, MethodArgumentNotValidException.class})
    ResponseEntity<Map<String, String>> malformedRequestHandler(){
        return new ResponseEntity<>(Map.of("description","Incorrect car data provided"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    ResponseEntity<Map<String, String>> duplicateEntityException() {
        return new ResponseEntity<>(Map.of("description", "Car already exists"), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CarExistsException.class)
    ResponseEntity<Map<String, String>> duplicateCarException() {
        return new ResponseEntity<>(Map.of("description", "Car already exists"), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidQueryException.class)
    ResponseEntity<Map<String, String>> invalidQueryException() {
        return new ResponseEntity<>(Map.of("description", "Incorrect query parameter provided"), HttpStatus.BAD_REQUEST);
    }


}
