package com.fundoo.exception;

import com.fundoo.label.exception.LabelException;
import com.fundoo.note.exception.ReminderException;
import com.fundoo.user.dto.ResponseDto;
import com.fundoo.user.exception.LoginUserException;
import com.fundoo.user.exception.RegistrationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class FundooExceptionHandler {

    @ExceptionHandler(value = RegistrationException.class)
    public ResponseEntity<Object> exceptionHandler(RegistrationException registrationException) {
        return new ResponseEntity<>(new ResponseDto(registrationException.message, registrationException.statusCode), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> methodNotSupported(HttpRequestMethodNotSupportedException e){
        return new ResponseEntity<>(new ResponseDto(e.getMessage(), 405), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = LoginUserException.class)
    public ResponseEntity<Object> loginExceptionHandler(LoginUserException loginException){
        return new ResponseEntity<>(new ResponseDto(loginException.getMessage(),400),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = LabelException.class)
    public ResponseEntity<Object> labelExceptionHandler(LabelException exception){
        return new ResponseEntity<>(new ResponseDto(exception.message,exception.statusCode),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(new ResponseDto(e.getBindingResult().getFieldError().getDefaultMessage(), 400), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Object> mediaTypeNotSupportExceptionHandler(HttpMediaTypeNotSupportedException e) {
        return new ResponseEntity<>(new ResponseDto("Unsupported Media Type", 400), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ReminderException.class)
    public ResponseEntity<Object> reminderException(ReminderException exception){
        return new ResponseEntity<>(new ResponseDto(exception.message,exception.statusCode),HttpStatus.BAD_REQUEST);
    }


}
