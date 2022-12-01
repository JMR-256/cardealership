package com.training.cardealership;

import com.training.cardealership.enums.ExceptionsEnum;
import com.training.cardealership.exceptions.CarExistsException;
import com.training.cardealership.exceptions.EntityNotFoundException;
import com.training.cardealership.exceptions.InvalidQueryException;
import com.training.cardealership.exceptions.ServiceException;
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

    @ExceptionHandler({HttpMessageNotReadableException.class, ConstraintViolationException.class, MethodArgumentNotValidException.class})
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

    @ExceptionHandler({InvalidQueryException.class, EntityNotFoundException.class})
    ResponseEntity<Map<String, String>> invalidQueryException(ServiceException exception) {
        ExceptionsEnum errorCode = exception.getErrorEnum();
        return new ResponseEntity<>(Map.of("description", errorCode.getDescription()), HttpStatus.BAD_REQUEST);
    }


}
