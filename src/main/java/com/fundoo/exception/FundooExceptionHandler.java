package com.fundoo.exception;

import com.fundoo.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.MultiValueMap;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class FundooExceptionHandler {

    @ExceptionHandler(value = RegistrationException.class)
    public ResponseEntity<Object> exceptionHandler(RegistrationException registrationException) {
        return new ResponseEntity<>(new ResponseDto(registrationException.message, registrationException.statusCode), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Object> mediaTypeNotSupportExceptionHandler(HttpMediaTypeNotSupportedException e) {
        return new ResponseEntity<>(new ResponseDto("Unsupported Media Type", 400), HttpStatus.BAD_REQUEST);
    }
}