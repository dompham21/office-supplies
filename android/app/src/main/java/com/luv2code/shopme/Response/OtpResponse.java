package com.luv2code.shopme.Response;

public class OtpResponse extends BaseResponse {
    private String email;
    private Integer otp;

    public OtpResponse() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }
}
