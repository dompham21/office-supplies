package com.luv2code.shopme.Response;

import com.google.gson.annotations.SerializedName;
import com.luv2code.shopme.Model.Address;
import com.luv2code.shopme.Model.Cart;

import java.util.List;

public class ListAddressResponse extends BaseResponse {
    @SerializedName("data")
    private List<Address> data;

    public ListAddressResponse() {
    }

    public List<Address> getData() {
        return data;
    }

    public void setData(List<Address> data) {
        this.data = data;
    }
}
