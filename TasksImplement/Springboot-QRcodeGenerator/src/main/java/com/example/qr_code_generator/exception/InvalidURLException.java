package com.example.qr_code_generator.exception;

public class InvalidURLException extends RuntimeException {
    public InvalidURLException(String message) {
        super(message);
    }
    
    public InvalidURLException(String message, Throwable cause) {
        super(message, cause);
    }
}