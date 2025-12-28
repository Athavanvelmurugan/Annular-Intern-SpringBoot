package com.example.qr_code_generator.controller;


import com.example.qr_code_generator.dto.ApiResponse;
import com.example.qr_code_generator.dto.QRCodeRequest;
import com.example.qr_code_generator.dto.QRCodeResponse;
import com.example.qr_code_generator.service.QRCodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qr")
@Tag(name = "QR Code Generator", description = "APIs for generating QR codes")
public class QRCodeController {
    
    private static final Logger logger = LoggerFactory.getLogger(QRCodeController.class);
    
    @Autowired
    private QRCodeService qrCodeService;
    
    @Operation(
        summary = "Generate QR code as Base64",
        description = "Generates QR code from URL and returns Base64 encoded image with metadata"
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", 
            description = "QR code generated successfully",
            content = @Content(schema = @Schema(implementation = ApiResponse.class))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400", 
            description = "Invalid URL provided"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "500", 
            description = "Internal server error"
        )
    })
    @PostMapping("/generate")
    public ResponseEntity<ApiResponse<QRCodeResponse>> generateQRCode(
            @Valid @RequestBody QRCodeRequest request,
            HttpServletRequest servletRequest) {
        
        logger.info("Received QR generation request for URL: {}", request.getUrl());
        
        QRCodeResponse response = qrCodeService.generateQRCode(request);
        
        ApiResponse<QRCodeResponse> apiResponse = ApiResponse.success(
            response,
            "QR code generated successfully",
            servletRequest.getRequestURI()
        );
        
        logger.info("QR code generated successfully for URL: {}", request.getUrl());
        
        return ResponseEntity.ok()
                .header("X-QR-Generated", "true")
                .body(apiResponse);
    }
    
    @Operation(
        summary = "Generate QR code (GET endpoint)",
        description = "Generates QR code from URL parameter and returns Base64 encoded image"
    )
    @GetMapping("/generate")
    public ResponseEntity<ApiResponse<QRCodeResponse>> generateQRCodeGet(
            @Parameter(description = "URL to encode", required = true, example = "https://myapp.com/verify/12345")
            @RequestParam String url,
            @Parameter(description = "Width of QR code", example = "300")
            @RequestParam(defaultValue = "300") int width,
            @Parameter(description = "Height of QR code", example = "300")
            @RequestParam(defaultValue = "300") int height,
            HttpServletRequest servletRequest) {
        
        logger.info("Received GET QR generation request for URL: {}, Size: {}x{}", url, width, height);
        
        QRCodeRequest request = new QRCodeRequest();
        request.setUrl(url);
        request.setWidth(width);
        request.setHeight(height);
        
        QRCodeResponse response = qrCodeService.generateQRCode(request);
        
        ApiResponse<QRCodeResponse> apiResponse = ApiResponse.success(
            response,
            "QR code generated successfully",
            servletRequest.getRequestURI()
        );
        
        return ResponseEntity.ok()
                .cacheControl(CacheControl.noCache())
                .header("X-Content-Type-Options", "nosniff")
                .body(apiResponse);
    }
    
    @Operation(
        summary = "Download QR code as PNG",
        description = "Generates QR code from URL and returns PNG image for download"
    )
    @GetMapping(value = "/download", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> downloadQRCode(
            @Parameter(description = "URL to encode", required = true)
            @RequestParam String url,
            @Parameter(description = "Width of QR code")
            @RequestParam(defaultValue = "300") int width,
            @Parameter(description = "Height of QR code")
            @RequestParam(defaultValue = "300") int height) {
        
        logger.info("Received QR download request for URL: {}, Size: {}x{}", url, width, height);
        
        byte[] qrCodeImage = qrCodeService.generateQRCodeImage(url, width, height);
        
        // Create filename from URL
        String filename = "qr_code_" + System.currentTimeMillis() + ".png";
        
        logger.info("QR code downloaded successfully. Size: {} bytes", qrCodeImage.length);
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, 
                       ContentDisposition.attachment()
                               .filename(filename)
                               .build()
                               .toString())
                .contentType(MediaType.IMAGE_PNG)
                .contentLength(qrCodeImage.length)
                .cacheControl(CacheControl.noCache())
                .header("X-Content-Type-Options", "nosniff")
                .header("X-Frame-Options", "DENY")
                .body(qrCodeImage);
    }
    
    @Operation(
        summary = "Display QR code inline",
        description = "Generates QR code from URL and returns PNG image for inline display"
    )
    @GetMapping(value = "/display", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> displayQRCode(
            @Parameter(description = "URL to encode", required = true)
            @RequestParam String url,
            @Parameter(description = "Width of QR code")
            @RequestParam(defaultValue = "300") int width,
            @Parameter(description = "Height of QR code")
            @RequestParam(defaultValue = "300") int height) {
        
        logger.info("Received QR display request for URL: {}, Size: {}x{}", url, width, height);
        
        byte[] qrCodeImage = qrCodeService.generateQRCodeImage(url, width, height);
        
        logger.debug("QR code displayed successfully. Size: {} bytes", qrCodeImage.length);
        
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .contentLength(qrCodeImage.length)
                .cacheControl(CacheControl.noCache())
                .header("X-Content-Type-Options", "nosniff")
                .body(qrCodeImage);
    }
}