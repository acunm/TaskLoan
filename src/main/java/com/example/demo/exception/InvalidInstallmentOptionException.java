package com.example.demo.exception;

public class InvalidInstallmentOptionException extends RuntimeException {
    public InvalidInstallmentOptionException(String msg) {
        super(msg);
    }

    public InvalidInstallmentOptionException(String msg, Throwable t) {
        super(msg, t);
    }
}
