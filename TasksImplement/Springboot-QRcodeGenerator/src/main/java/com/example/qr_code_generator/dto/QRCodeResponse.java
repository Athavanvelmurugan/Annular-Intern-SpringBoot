package com.example.qr_code_generator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "Response DTO for QR code generation")
public class QRCodeResponse {

    // Getters
    @Schema(description = "Base64 encoded QR code image",
            example = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAA...")
    private String base64Image;
    
    @Schema(description = "QR code image URL for download")
    private String downloadUrl;
    
    @Schema(description = "Original URL encoded in QR")
    private String encodedUrl;
    
    @Schema(description = "File size in bytes")
    private long fileSize;
    
    // Constructor
    public QRCodeResponse(String base64Image, String encodedUrl, long fileSize) {
        this.base64Image = base64Image;
        this.encodedUrl = encodedUrl;
        this.fileSize = fileSize;
        this.downloadUrl = "/api/qr/download?url=" + 
                          java.net.URLEncoder.encode(encodedUrl, java.nio.charset.StandardCharsets.UTF_8);
    }

}