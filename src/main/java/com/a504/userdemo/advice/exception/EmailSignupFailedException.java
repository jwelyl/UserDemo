package com.a504.userdemo.advice.exception;

public class EmailSignupFailedException extends RuntimeException {
    public EmailSignupFailedException() {}

    public EmailSignupFailedException(String message) {
        super(message);
    }

    public EmailSignupFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
