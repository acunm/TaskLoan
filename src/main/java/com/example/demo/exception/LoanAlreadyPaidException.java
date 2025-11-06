package com.example.demo.exception;

public class LoanAlreadyPaidException extends RuntimeException {
    public LoanAlreadyPaidException(String message) {
        super(message);
    }

    public LoanAlreadyPaidException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
