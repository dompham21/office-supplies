package com.luv2code.shopme.Request;

public class ChangePhoneRequest {
    String phone;

    public ChangePhoneRequest() {
    }

    public ChangePhoneRequest(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
