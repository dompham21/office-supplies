package com.luv2code.shopme.Response;

import com.google.gson.annotations.SerializedName;
import com.luv2code.shopme.Model.Order;

import java.util.List;

public class ListOrderResponse extends BaseResponse {
    @SerializedName("data")
    List<Order> data;

    public ListOrderResponse() {
    }

    public List<Order> getData() {
        return data;
    }

    public void setData(List<Order> data) {
        this.data = data;
    }
}
