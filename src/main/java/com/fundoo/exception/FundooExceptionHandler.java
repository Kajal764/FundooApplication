package com.fundoo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class FundooExceptionHandler {

    @ExceptionHandler(value = RegistrationException.class)
    public ResponseEntity<Object> exceptionHandler(RegistrationException registrationException) {
        return new ResponseEntity<>(registrationException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
