package com.luv2code.shopme.Request;

public class ChangeNameRequest {
    String name;

    public ChangeNameRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
