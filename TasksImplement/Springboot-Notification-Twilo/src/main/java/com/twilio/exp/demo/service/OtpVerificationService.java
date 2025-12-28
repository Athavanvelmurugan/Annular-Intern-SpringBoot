package com.twilio.exp.demo.service;

import com.twilio.exp.demo.entity.OtpVerification;
import com.twilio.exp.demo.repository.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;

@Service
public class OtpVerificationService {

    @Autowired
    private OtpRepository otpRepository;

    public boolean verifyOtp(String phoneNumber, String otp) {

        // Normalize phone number
        if (!phoneNumber.startsWith("+")) {
            phoneNumber = "+91" + phoneNumber;
        }

        // Get the latest OTP for this number
        OtpVerification otpData = otpRepository
                .findTopByPhoneNumberOrderByExpiryTimeDesc(phoneNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "OTP not found"));

        // Check expiry (using UTC)
        Instant now = Instant.now();
        if (otpData.getExpiryTime().isBefore(now)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OTP expired");
        }

        // Check OTP match
        if (!otpData.getOtp().equals(otp)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid OTP");
        }

        // Delete OTP after successful verification (one-time use)
        otpRepository.delete(otpData);

        return true;
    }
}
