package com.laba.solvd.entities.exceptions;

public class NullEntitySetException extends RuntimeException{
    public NullEntitySetException(String message) {
        super(message);
    }

    public NullEntitySetException(String message, Throwable cause) {
        super(message, cause);
    }
}
