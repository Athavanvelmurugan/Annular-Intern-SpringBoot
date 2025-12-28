package com.twilio.exp.demo.service;

import com.twilio.exp.demo.entity.OtpVerification;
import com.twilio.exp.demo.repository.OtpRepository;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private OtpRepository otpRepository;

    @Value("${twilio.phone_number}")
    private String twilioNumber;

    // OTP validity in minutes
    private final int OTP_EXPIRY_MINUTES = 5;

    public void sendOtp(String phoneNumber) {


        if (!phoneNumber.startsWith("+")) {
            phoneNumber = "+91" + phoneNumber;
        }


        otpRepository.deleteByPhoneNumber(phoneNumber);


        String otp = String.valueOf(new Random().nextInt(900000) + 100000);


        OtpVerification otpEntity = new OtpVerification();
        otpEntity.setPhoneNumber(phoneNumber);
        otpEntity.setOtp(otp);
        otpEntity.setExpiryTime(Instant.now().plus(OTP_EXPIRY_MINUTES, ChronoUnit.MINUTES));
        otpRepository.save(otpEntity);


        Message.creator(
                new PhoneNumber(phoneNumber),
                new PhoneNumber(twilioNumber),
                "Your OTP is: " + otp
        ).create();
    }
}
