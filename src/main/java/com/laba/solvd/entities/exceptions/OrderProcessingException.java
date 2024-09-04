package com.laba.solvd.entities.exceptions;

public class OrderProcessingException extends BaseException{
    public OrderProcessingException(String message) {
        super(message);
    }

    public OrderProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
