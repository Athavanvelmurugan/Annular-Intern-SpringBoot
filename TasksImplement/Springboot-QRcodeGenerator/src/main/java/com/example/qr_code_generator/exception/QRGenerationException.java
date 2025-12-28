package com.example.qr_code_generator.exception;

public class QRGenerationException extends RuntimeException {
    public QRGenerationException(String message) {
        super(message);
    }
    
    public QRGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}