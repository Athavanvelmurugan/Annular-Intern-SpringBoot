package com.twilio.exp.demo.repository;

import com.twilio.exp.demo.entity.OtpVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface OtpRepository extends JpaRepository<OtpVerification, Long> {

    Optional<OtpVerification> findTopByPhoneNumberOrderByExpiryTimeDesc(String phoneNumber);
    void deleteByPhoneNumber(String phoneNumber);

}
