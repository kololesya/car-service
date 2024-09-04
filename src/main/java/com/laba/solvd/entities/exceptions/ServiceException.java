package com.laba.solvd.entities.exceptions;

public class ServiceException extends BaseException{
    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
