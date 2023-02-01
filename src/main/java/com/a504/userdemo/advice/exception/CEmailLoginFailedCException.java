package com.a504.userdemo.advice.exception;

public class CEmailLoginFailedCException extends RuntimeException {
    public CEmailLoginFailedCException() {
        super();
    }

    public CEmailLoginFailedCException(String message) {
        super(message);
    }

    public CEmailLoginFailedCException(String message, Throwable cause) {
        super(message, cause);
    }
}
