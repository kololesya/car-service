package com.laba.solvd.entities.exceptions;

public class InvalidCarException extends RuntimeException{
    public InvalidCarException(String message) {
        super(message);
    }

    public InvalidCarException(String message, Throwable cause) {
        super(message, cause);
    }
}
