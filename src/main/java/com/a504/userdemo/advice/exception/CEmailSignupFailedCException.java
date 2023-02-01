package com.a504.userdemo.advice.exception;

public class CEmailSignupFailedCException extends RuntimeException {
    public CEmailSignupFailedCException() {}

    public CEmailSignupFailedCException(String message) {
        super(message);
    }

    public CEmailSignupFailedCException(String message, Throwable cause) {
        super(message, cause);
    }
}
