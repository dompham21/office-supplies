package com.luv2code.shopme.Response;

import com.google.gson.annotations.SerializedName;
import com.luv2code.shopme.Model.Cart;
import com.luv2code.shopme.Model.Product;

public class CartResponse extends BaseResponse {
    @SerializedName("data")
    private Cart data;

    public CartResponse() {
    }

    public Cart getData() {
        return data;
    }

    public void setData(Cart data) {
        this.data = data;
    }
}
