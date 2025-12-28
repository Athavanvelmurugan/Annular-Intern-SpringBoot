package com.example.qr_code_generator.utill;


import com.example.qr_code_generator.exception.InvalidURLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Pattern;

@Component
public class ValidationUtil {
    
    private static final Logger logger = LoggerFactory.getLogger(ValidationUtil.class);
    private static final Pattern URL_PATTERN = Pattern.compile(
        "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$", 
        Pattern.CASE_INSENSITIVE
    );
    
    /**
     * Validate URL format and accessibility
     */
    public void validateURL(String url) {
        logger.debug("Validating URL: {}", url);
        
        if (url == null || url.trim().isEmpty()) {
            throw new InvalidURLException("URL cannot be null or empty");
        }
        
        // Check URL pattern
        if (!URL_PATTERN.matcher(url).matches()) {
            throw new InvalidURLException("Invalid URL format: " + url);
        }
        
        try {
            // Parse URL to validate format
            URI uri = new URI(url);
            
            // Check if URL has valid scheme
            String scheme = uri.getScheme();
            if (scheme == null || (!scheme.equalsIgnoreCase("http") && 
                                   !scheme.equalsIgnoreCase("https"))) {
                throw new InvalidURLException("URL must use HTTP or HTTPS protocol");
            }
            
            // Check if URL has host
            if (uri.getHost() == null) {
                throw new InvalidURLException("URL must contain a host");
            }
            
            logger.info("URL validation successful: {}", url);
            
        } catch (URISyntaxException e) {
            logger.error("Invalid URL syntax: {}", url, e);
            throw new InvalidURLException("Invalid URL syntax: " + e.getMessage());
        }
    }
}