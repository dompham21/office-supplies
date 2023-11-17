package com.luv2code.shopme.Model;

import java.io.Serializable;

public class Address implements Serializable {
    private Integer id;
    private String specificAddress;
    private String name;
    private String email;
    private String phone;
    private boolean default_address;
    private Ward ward;


    public Address(Integer id, String specificAddress, String name, String email, String phone, boolean isDefault, String province, String district, Ward ward) {
        this.id = id;
        this.specificAddress = specificAddress;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.default_address = isDefault;
        this.ward = ward;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSpecificAddress() {
        return specificAddress;
    }

    public void setSpecificAddress(String specificAddress) {
        this.specificAddress = specificAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isDefault_address() {
        return default_address;
    }

    public void setDefault_address(boolean default_address) {
        this.default_address = default_address;
    }

    public Ward getWard() {
        return ward;
    }

    public void setWard(Ward ward) {
        this.ward = ward;
    }
}
