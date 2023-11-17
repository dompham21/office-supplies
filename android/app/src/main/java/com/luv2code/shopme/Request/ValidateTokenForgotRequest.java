package com.luv2code.shopme.Request;

public class ValidateTokenForgotRequest {
    private int otp;
    private String email;

    public ValidateTokenForgotRequest(int otp, String email) {
        this.otp = otp;
        this.email = email;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
