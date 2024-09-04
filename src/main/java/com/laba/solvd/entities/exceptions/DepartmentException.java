package com.laba.solvd.entities.exceptions;

public class DepartmentException extends RuntimeException{

    public DepartmentException() {
        super();
    }

    public DepartmentException(String message) {
        super(message);
    }

    public DepartmentException(String message, Throwable cause) {
        super(message, cause);
    }

    public DepartmentException(Throwable cause) {
        super(cause);
    }
}
