package com.trustrent.backend.dto;

public class RegisterResponse {
    public String message;
    public String email;

    // FOR DEVELOPMENT ONLY - Return OTP in response so frontend testing is easy without a real mail server
    public String devOtp;

    public RegisterResponse(String message, String email, String devOtp) {
        this.message = message;
        this.email = email;
        this.devOtp = devOtp;
    }
}
