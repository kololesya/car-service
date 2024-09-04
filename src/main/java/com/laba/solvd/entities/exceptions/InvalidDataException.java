package com.laba.solvd.entities.exceptions;

public class InvalidDataException extends BaseException{
    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
