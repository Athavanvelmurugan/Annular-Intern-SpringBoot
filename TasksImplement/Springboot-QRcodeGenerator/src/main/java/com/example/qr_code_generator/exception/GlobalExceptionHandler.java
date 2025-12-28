package com.example.qr_code_generator.exception;


import com.example.qr_code_generator.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    @ExceptionHandler(InvalidURLException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidURLException(
            InvalidURLException ex, HttpServletRequest request) {
        
        logger.error("Invalid URL exception: {}", ex.getMessage());
        
        ApiResponse<Void> response = ApiResponse.error(
            ex.getMessage(),
            request.getRequestURI()
        );
        
        return ResponseEntity.badRequest().body(response);
    }
    
    @ExceptionHandler(QRGenerationException.class)
    public ResponseEntity<ApiResponse<Void>> handleQRGenerationException(
            QRGenerationException ex, HttpServletRequest request) {
        
        logger.error("QR Generation exception: {}", ex.getMessage(), ex);
        
        ApiResponse<Void> response = ApiResponse.error(
            "Failed to generate QR code: " + ex.getMessage(),
            request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationExceptions(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        
        logger.error("Validation exception: {}", errorMessage);
        
        ApiResponse<Void> response = ApiResponse.error(
            errorMessage,
            request.getRequestURI()
        );
        
        return ResponseEntity.badRequest().body(response);
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleConstraintViolationException(
            ConstraintViolationException ex, HttpServletRequest request) {
        
        String errorMessage = ex.getConstraintViolations()
                .stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining(", "));
        
        logger.error("Constraint violation: {}", errorMessage);
        
        ApiResponse<Void> response = ApiResponse.error(
            errorMessage,
            request.getRequestURI()
        );
        
        return ResponseEntity.badRequest().body(response);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGlobalException(
            Exception ex, HttpServletRequest request) {
        
        logger.error("Unexpected error: {}", ex.getMessage(), ex);
        
        ApiResponse<Void> response = ApiResponse.error(
            "Internal server error: " + ex.getMessage(),
            request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}