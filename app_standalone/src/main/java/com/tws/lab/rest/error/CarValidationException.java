package com.tws.lab.rest.error;

public class CarValidationException extends RuntimeException {
    public CarValidationException(String message) {
        super(message);
    }
} 