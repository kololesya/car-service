package com.laba.solvd.entities.exceptions;

public class InvalidServiceException extends RuntimeException {
    public InvalidServiceException(String message) {
        super(message);
    }

    public InvalidServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
