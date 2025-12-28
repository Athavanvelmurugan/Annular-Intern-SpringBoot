package com.thamizh.Springboot.Validation.Service;

import com.thamizh.Springboot.Validation.DTO.SignupResponse;
import com.thamizh.Springboot.Validation.DTO.signupRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class StudentService {


    public SignupResponse addstudent(signupRequest signupRequest) {
        return new SignupResponse<>("Student are created Successfully " + signupRequest.getUsername());
    }

}
