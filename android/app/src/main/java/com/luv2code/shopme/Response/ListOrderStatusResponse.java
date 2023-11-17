package com.luv2code.shopme.Response;

import com.google.gson.annotations.SerializedName;
import com.luv2code.shopme.Model.District;
import com.luv2code.shopme.Model.OrderStatus;

import java.util.List;

public class ListOrderStatusResponse extends BaseResponse {
    @SerializedName("data")
    List<OrderStatus> data;

    public ListOrderStatusResponse() {
    }

    public List<OrderStatus> getData() {
        return data;
    }

    public void setData(List<OrderStatus> data) {
        this.data = data;
    }
}
