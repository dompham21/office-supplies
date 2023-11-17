package com.luv2code.shopme.Request;

public class ChangeEmailRequest {
    String email;

    public ChangeEmailRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
