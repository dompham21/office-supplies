package com.luv2code.shopme.Request;

public class AddressRequest {
    String name;
    String phone;
    String specificAddress;
    String wardCode;
    Boolean isDefault;

    public AddressRequest(String name, String phone, String specificAddress, String wardCode, Boolean isDefault) {
        this.name = name;
        this.phone = phone;
        this.specificAddress = specificAddress;
        this.wardCode = wardCode;
        this.isDefault = isDefault;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSpecificAddress() {
        return specificAddress;
    }

    public void setSpecificAddress(String specificAddress) {
        this.specificAddress = specificAddress;
    }

    public String getWardCode() {
        return wardCode;
    }

    public void setWardCode(String wardCode) {
        this.wardCode = wardCode;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }
}
