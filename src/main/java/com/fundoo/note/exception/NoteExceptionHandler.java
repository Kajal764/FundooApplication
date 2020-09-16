package com.fundoo.note.exception;

import com.fundoo.user.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NoteExceptionHandler {

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<Object> authenticationException(AuthenticationException authenticationException){
        return new ResponseEntity<>(new ResponseDto(authenticationException.getMessage(),403),HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = NoteException.class)
    public ResponseEntity<Object> noteException(NoteException noteException){
        return new ResponseEntity<>(new ResponseDto(noteException.getMessage(),400),HttpStatus.FORBIDDEN);
    }
}
