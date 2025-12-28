package com.example.qr_code_generator.service;


import com.example.qr_code_generator.dto.QRCodeRequest;
import com.example.qr_code_generator.dto.QRCodeResponse;

public interface QRCodeService {
    QRCodeResponse generateQRCode(QRCodeRequest request);
    byte[] generateQRCodeImage(String url, int width, int height);
    String generateQRCodeBase64(String url, int width, int height);
}