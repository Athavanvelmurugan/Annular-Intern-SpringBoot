package com.example.qr_code_generator.utill;


import com.example.qr_code_generator.exception.QRGenerationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Component
public class FileStorageUtil {
    
    private static final Logger logger = LoggerFactory.getLogger(FileStorageUtil.class);
    private static final DateTimeFormatter FORMATTER = 
        DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    
    @Value("${qr.storage.path:./qr-codes}")
    private String storagePath;
    
    /**
     * Store QR code to file system
     */
    public String storeQRCode(byte[] qrCodeBytes, String url) {
        try {
            // Create directory if not exists
            Path directory = Paths.get(storagePath);
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
                logger.info("Created storage directory: {}", directory.toAbsolutePath());
            }
            
            // Generate unique filename
            String timestamp = LocalDateTime.now().format(FORMATTER);
            String urlHash = Base64.getUrlEncoder().encodeToString(url.getBytes());
            String safeUrlHash = urlHash.replaceAll("[^a-zA-Z0-9]", "").substring(0, 10);
            String filename = "qr_" + safeUrlHash + "_" + timestamp + ".png";
            
            // Write file
            Path filePath = directory.resolve(filename);
            Files.write(filePath, qrCodeBytes);
            
            logger.info("QR code saved to: {}", filePath.toAbsolutePath());
            return filePath.toString();
            
        } catch (IOException e) {
            logger.error("Failed to store QR code: {}", e.getMessage(), e);
            throw new QRGenerationException("Failed to store QR code: " + e.getMessage(), e);
        }
    }
    
    /**
     * Read QR code from file system
     */
    public byte[] readQRCode(String filePath) {
        try {
            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
                throw new QRGenerationException("QR code file not found: " + filePath);
            }
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new QRGenerationException("Failed to read QR code: " + e.getMessage(), e);
        }
    }
}