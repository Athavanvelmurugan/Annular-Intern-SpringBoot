package com.thamizh.Springboot.Validation.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class signupRequest {

    @NotBlank @Size(min = 5, max = 20)
    private String username;
    @NotBlank @DateTimeFormat
    private String dob;
    @NotBlank @Size(min = 6 , max = 12)
    private String password;
    @NotBlank
    private String category;
    @NotBlank @Email
    private String email;

}
