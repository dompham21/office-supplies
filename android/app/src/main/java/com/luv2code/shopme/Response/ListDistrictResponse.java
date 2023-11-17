package com.luv2code.shopme.Response;

import com.google.gson.annotations.SerializedName;
import com.luv2code.shopme.Model.District;

import java.util.List;

public class ListDistrictResponse extends BaseResponse {
    @SerializedName("data")
    List<District> data;

    public ListDistrictResponse() {
    }

    public List<District> getData() {
        return data;
    }

    public void setData(List<District> data) {
        this.data = data;
    }
}
