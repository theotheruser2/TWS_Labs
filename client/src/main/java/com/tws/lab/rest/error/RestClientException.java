package com.tws.lab.rest.error;

public class RestClientException extends RuntimeException {
    public RestClientException(String message) {
        super(message);
    }

    public RestClientException(String message, Throwable cause) {
        super(message, cause);
    }
} 