package com.innowise.authservice.exception;

public class UserDataException extends RuntimeException {
    public UserDataException() {
    }

    public UserDataException(String message) {
        super(message);
    }

    public UserDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserDataException(Throwable cause) {
        super(cause);
    }
}
