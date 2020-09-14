package com.fundoo.user.exception;

public class RegistrationException extends RuntimeException {

    public String message;
    public int statusCode;

    public RegistrationException(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
