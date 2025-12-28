package com.example.qr_code_generator.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Standard API response format")
public class ApiResponse<T> {
    
    @Schema(description = "Response status", example = "success")
    private String status;
    
    @Schema(description = "Response message", example = "QR code generated successfully")
    private String message;
    
    @Schema(description = "Response data")
    private T data;
    
    @Schema(description = "Response timestamp")
    private LocalDateTime timestamp;
    
    @Schema(description = "Request path")
    private String path;
    
    // Success response factory method
    public static <T> ApiResponse<T> success(T data, String message, String path) {
        return new ApiResponse<>("success", message, data, LocalDateTime.now(), path);
    }
    
    // Error response factory method
    public static <T> ApiResponse<T> error(String message, String path) {
        return new ApiResponse<>("error", message, null, LocalDateTime.now(), path);
    }
}