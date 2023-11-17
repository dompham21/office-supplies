package com.luv2code.shopme.Response;

import com.google.gson.annotations.SerializedName;
import com.luv2code.shopme.Model.Order;

public class OrderResponse extends BaseResponse {
    @SerializedName("data")
    private Order data;

    public OrderResponse() {
    }

    public Order getData() {
        return data;
    }

    public void setData(Order data) {
        this.data = data;
    }
}
