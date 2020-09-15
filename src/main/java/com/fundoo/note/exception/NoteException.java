package com.fundoo.note.exception;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NoteException extends Throwable {
    public final String message;
    public final int statusCode;

}
