package com.luv2code.shopme.Request;

public class GenerateTokenForgotPassword {
    String email;

    public GenerateTokenForgotPassword(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
