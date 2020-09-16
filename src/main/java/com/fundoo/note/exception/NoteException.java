package com.fundoo.note.exception;



public class NoteException extends Throwable {
    public String message;

    public NoteException(String message){
        super(message);
    }


}
