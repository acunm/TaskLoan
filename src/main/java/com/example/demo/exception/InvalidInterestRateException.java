package com.example.demo.exception;

public class InvalidInterestRateException extends RuntimeException {
    public InvalidInterestRateException(String msg) {
        super(msg);
    }

    public InvalidInterestRateException(String msg, Throwable t) {
        super(msg, t);
    }
}
