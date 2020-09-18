package com.fundoo.label.exception;

public class LabelException extends RuntimeException {

        public String message;
        public int statusCode;

        public LabelException(String message, int statusCode) {
            this.message = message;
            this.statusCode = statusCode;
        }


}
