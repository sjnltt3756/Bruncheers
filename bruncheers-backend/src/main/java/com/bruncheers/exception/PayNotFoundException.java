package com.bruncheers.exception;

public class PayNotFoundException extends RuntimeException {
    public PayNotFoundException(String message) {
        super(message);
    }
}
