package com.example.demo.exception;

public class CustomerLimitIsNotEnoughException extends RuntimeException {
    public CustomerLimitIsNotEnoughException(String msg) {
        super(msg);
    }

    public CustomerLimitIsNotEnoughException(String msg, Throwable t) {
        super(msg, t);
    }
}
