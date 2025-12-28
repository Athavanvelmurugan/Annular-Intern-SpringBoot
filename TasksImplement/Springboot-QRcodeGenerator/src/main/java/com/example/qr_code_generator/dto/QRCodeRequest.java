package com.example.qr_code_generator.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request DTO for QR code generation")
public class QRCodeRequest {
    
    @NotBlank(message = "URL is required")
    @Pattern(regexp = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$", 
             message = "Invalid URL format")
    @Schema(description = "URL to encode in QR code", 
            example = "https://myapp.com/verify/12345", 
            required = true)
    private String url;
    
    @Schema(description = "Width of QR code in pixels", 
            example = "300", 
            defaultValue = "300")
    private int width = 300;
    
    @Schema(description = "Height of QR code in pixels", 
            example = "300", 
            defaultValue = "300")
    private int height = 300;
    
    // Getters and Setters
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public int getWidth() {
        return width;
    }
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
}