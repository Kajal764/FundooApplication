package com.fundoo.note.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReminderException extends RuntimeException {
    public String message;
    public int statusCode;

}
