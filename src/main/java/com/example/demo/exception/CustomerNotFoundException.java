package com.example.demo.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String message) {
        super(message);
    }

    public CustomerNotFoundException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
