package com.luv2code.shopme.Response;

import com.google.gson.annotations.SerializedName;
import com.luv2code.shopme.Model.Address;

public class AddressResponse extends BaseResponse {
    @SerializedName("data")
    private Address data;

    public AddressResponse() {
    }

    public Address getData() {
        return data;
    }

    public void setData(Address data) {
        this.data = data;
    }
}
