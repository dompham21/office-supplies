package com.luv2code.shopme.Response;

import com.google.gson.annotations.SerializedName;
import com.luv2code.shopme.Model.Poster;
import com.luv2code.shopme.Model.Product;

import java.util.List;

public class ListPostersResponse extends BaseResponse{
    @SerializedName("data")
    List<Poster> data;


    public ListPostersResponse() {
    }

    public List<Poster> getData() {
        return data;
    }

    public void setData(List<Poster> data) {
        this.data = data;
    }


}
