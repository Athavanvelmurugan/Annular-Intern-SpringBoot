package com.twilio.exp.demo.controller;

import com.twilio.exp.demo.service.OtpService;
import com.twilio.exp.demo.service.OtpVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/otp")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @Autowired
    private OtpVerificationService otpVerificationService;

    @PostMapping("/send")
    public ResponseEntity<?> sendOtp(@RequestParam String phoneNumber) {
        otpService.sendOtp(phoneNumber);
        return ResponseEntity.ok("OTP sent successfully");
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyOtp(
            @RequestParam String phoneNumber,
            @RequestParam String otp) {

        boolean isValid = otpVerificationService.verifyOtp(phoneNumber, otp);
        return ResponseEntity.ok("OTP verified successfully");
    }
}

