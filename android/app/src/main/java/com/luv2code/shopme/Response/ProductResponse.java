package com.luv2code.shopme.Response;

import com.google.gson.annotations.SerializedName;
import com.luv2code.shopme.Model.Product;

public class ProductResponse extends BaseResponse {
    @SerializedName("data")
    private Product data;


    public ProductResponse() {
    }

    public Product getData() {
        return data;
    }

    public void setData(Product data) {
        this.data = data;
    }
}
