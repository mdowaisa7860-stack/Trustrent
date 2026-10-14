package com.trustrent.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class OtpVerificationRequest {
    @NotBlank @Email public String email;
    @NotBlank public String otp;
}
