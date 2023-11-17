package com.luv2code.shopme.Response;

import com.google.gson.annotations.SerializedName;
import com.luv2code.shopme.Model.District;
import com.luv2code.shopme.Model.Province;

import java.util.List;

public class ListProvinceResponse extends BaseResponse {
    @SerializedName("data")
    List<Province> data;

    public ListProvinceResponse() {
    }

    public List<Province> getData() {
        return data;
    }

    public void setData(List<Province> data) {
        this.data = data;
    }
}
