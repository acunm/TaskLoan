package com.example.demo.exception;

public class NotEnoughBalanceException extends RuntimeException {
    public NotEnoughBalanceException(String message) {
        super(message);
    }

    public NotEnoughBalanceException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
