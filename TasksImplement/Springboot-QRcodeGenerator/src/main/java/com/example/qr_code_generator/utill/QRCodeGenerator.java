package com.example.qr_code_generator.utill;


import com.example.qr_code_generator.exception.QRGenerationException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class QRCodeGenerator {
    
    private static final Logger logger = LoggerFactory.getLogger(QRCodeGenerator.class);
    private static final String IMAGE_FORMAT = "PNG";
    private static final String CHARSET = "UTF-8";
    
    /**
     * Generate QR code as byte array
     */
    public byte[] generateQRCodeImage(String text, int width, int height) {
        logger.debug("Generating QR code for text: {}, Size: {}x{}", 
                    text, width, height);
        
        try {
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
            hints.put(EncodeHintType.MARGIN, 1);
            
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);
            
            // Convert to BufferedImage
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            
            // Add white border for better scanning
            bufferedImage = addBorder(bufferedImage, 10, Color.WHITE);
            
            // Convert to byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, IMAGE_FORMAT, baos);
            
            byte[] imageBytes = baos.toByteArray();
            logger.info("QR code generated successfully. Size: {} bytes", imageBytes.length);
            
            return imageBytes;
            
        } catch (Exception e) {
            logger.error("Failed to generate QR code: {}", e.getMessage(), e);
            throw new QRGenerationException("Failed to generate QR code: " + e.getMessage(), e);
        }
    }
    
    /**
     * Generate QR code as Base64 string
     */
    public String generateQRCodeBase64(String text, int width, int height) {
        byte[] imageBytes = generateQRCodeImage(text, width, height);
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
        return "data:image/png;base64," + base64Image;
    }
    
    /**
     * Add border to image for better scanning
     */
    private BufferedImage addBorder(BufferedImage image, int borderSize, Color borderColor) {
        int newWidth = image.getWidth() + (borderSize * 2);
        int newHeight = image.getHeight() + (borderSize * 2);
        
        BufferedImage borderedImage = new BufferedImage(
            newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        
        Graphics2D g = borderedImage.createGraphics();
        g.setColor(borderColor);
        g.fillRect(0, 0, newWidth, newHeight);
        g.drawImage(image, borderSize, borderSize, null);
        g.dispose();
        
        return borderedImage;
    }
}