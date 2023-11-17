package com.luv2code.shopme.Request;

public class VerifyTokenNewPasswordRequest {
    private String newPassword;
    private int otp;
    private String email;

    public VerifyTokenNewPasswordRequest(String newPassword, int otp, String email) {
        this.newPassword = newPassword;
        this.otp = otp;
        this.email = email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
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
