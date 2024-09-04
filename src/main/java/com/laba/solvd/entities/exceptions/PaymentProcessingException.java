package com.laba.solvd.entities.exceptions;

public class PaymentProcessingException extends BaseException {
    public PaymentProcessingException(String message) {
        super(message);
    }

    public PaymentProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
