package com.luv2code.shopme.Response;

import com.google.gson.annotations.SerializedName;
import com.luv2code.shopme.Model.Product;
import com.luv2code.shopme.Model.Ward;

import java.util.List;

public class ListWardResponse extends BaseResponse {
    @SerializedName("data")
    List<Ward> data;

    public ListWardResponse() {
    }

    public List<Ward> getData() {
        return data;
    }

    public void setData(List<Ward> data) {
        this.data = data;
    }
}
