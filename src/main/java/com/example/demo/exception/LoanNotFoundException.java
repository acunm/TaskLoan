package com.example.demo.exception;

public class LoanNotFoundException extends RuntimeException {
    public LoanNotFoundException(String message) {
        super(message);
    }

    public LoanNotFoundException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
