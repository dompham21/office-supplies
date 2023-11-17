package com.luv2code.shopme.Response;

import com.google.gson.annotations.SerializedName;
import com.luv2code.shopme.Model.Cart;
import com.luv2code.shopme.Model.Poster;

import java.util.List;

public class ListCartResponse extends BaseResponse {
    @SerializedName("data")
    List<Cart> data;

    @SerializedName("total")
    double total;

    public ListCartResponse() {
    }

    public List<Cart> getData() {
        return data;
    }

    public void setData(List<Cart> data) {
        this.data = data;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
