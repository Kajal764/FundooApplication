package com.fundoo.exception;

public class RegistrationException extends RuntimeException {

    public ExceptionType type;

    public enum ExceptionType {
        ALREADY_REGISTER
    }

    public RegistrationException(ExceptionType type) {
        this.type = type;
    }

}
