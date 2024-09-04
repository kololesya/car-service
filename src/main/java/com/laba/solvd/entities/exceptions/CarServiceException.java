package com.laba.solvd.entities.exceptions;

public class CarServiceException extends RuntimeException{
    public CarServiceException() {
        super();
    }

    public CarServiceException(String message) {
        super(message);
    }

    public CarServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public CarServiceException(Throwable cause) {
        super(cause);
    }
}
