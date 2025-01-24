package com.tws.lab.exception;

public class ThrottlingException extends RuntimeException {
    public ThrottlingException(String message) {
        super(message);
    }
} 