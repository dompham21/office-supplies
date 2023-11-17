package com.luv2code.shopme.Request;

public class OTPRequest {
    int otp;

    public OTPRequest(int otp) {
        this.otp = otp;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }
}
