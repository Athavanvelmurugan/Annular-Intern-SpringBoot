package com.example.qr_code_generator.service.impl;


import com.example.qr_code_generator.dto.QRCodeRequest;
import com.example.qr_code_generator.dto.QRCodeResponse;
import com.example.qr_code_generator.service.QRCodeService;
import com.example.qr_code_generator.utill.FileStorageUtil;
import com.example.qr_code_generator.utill.QRCodeGenerator;
import com.example.qr_code_generator.utill.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class QRCodeServiceImpl implements QRCodeService {
    
    private static final Logger logger = LoggerFactory.getLogger(QRCodeServiceImpl.class);
    
    @Autowired
    private QRCodeGenerator qrCodeGenerator;
    
    @Autowired
    private ValidationUtil validationUtil;
    
    @Autowired
    private FileStorageUtil fileStorageUtil;
    
    @Override
    @Cacheable(value = "qrCodes", key = "#request.url + '-' + #request.width + 'x' + #request.height")
    public QRCodeResponse generateQRCode(QRCodeRequest request) {
        logger.info("Generating QR code for URL: {}", request.getUrl());
        
        // Validate URL
        validationUtil.validateURL(request.getUrl());
        
        // Generate QR code as Base64
        String base64Image = qrCodeGenerator.generateQRCodeBase64(
            request.getUrl(), 
            request.getWidth(), 
            request.getHeight()
        );
        
        // Generate QR code as bytes for storage
        byte[] qrCodeBytes = qrCodeGenerator.generateQRCodeImage(
            request.getUrl(),
            request.getWidth(),
            request.getHeight()
        );
        
        // Store to file system (optional - for audit/logging)
        String storedPath = fileStorageUtil.storeQRCode(qrCodeBytes, request.getUrl());
        logger.debug("QR code stored at: {}", storedPath);
        
        // Create response
        return new QRCodeResponse(
            base64Image,
            request.getUrl(),
            qrCodeBytes.length
        );
    }
    
    @Override
    @Cacheable(value = "qrCodeImages", key = "#url + '-' + #width + 'x' + #height")
    public byte[] generateQRCodeImage(String url, int width, int height) {
        logger.info("Generating QR code image for URL: {}, Size: {}x{}", url, width, height);
        
        // Validate URL
        validationUtil.validateURL(url);
        
        // Generate QR code
        return qrCodeGenerator.generateQRCodeImage(url, width, height);
    }
    
    @Override
    @Cacheable(value = "qrCodeBase64", key = "#url + '-' + #width + 'x' + #height")
    public String generateQRCodeBase64(String url, int width, int height) {
        logger.info("Generating QR code Base64 for URL: {}, Size: {}x{}", url, width, height);
        
        // Validate URL
        validationUtil.validateURL(url);
        
        // Generate QR code
        return qrCodeGenerator.generateQRCodeBase64(url, width, height);
    }
}