package com.thamizh.Springboot.Validation.Controller;

import com.thamizh.Springboot.Validation.DTO.SignupResponse;
import com.thamizh.Springboot.Validation.DTO.signupRequest;
import com.thamizh.Springboot.Validation.Service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class studentcontroller {

    @Autowired
    private StudentService studentservice;

    @PostMapping
    public ResponseEntity<SignupResponse> signup(@Valid @RequestBody signupRequest signupRequest) {
        SignupResponse signupResponse = studentservice.addstudent(signupRequest);
        return ResponseEntity.ok().body(signupResponse);
    }
}
