package com.luv2code.shopme.Response;

import com.google.gson.annotations.SerializedName;
import com.luv2code.shopme.Model.Category;

import java.util.List;

public class ListCategoryResponse extends BaseResponse{
    @SerializedName("data")
    List<Category> data;

    @SerializedName("totalPage")
    Integer totalPage;

    public ListCategoryResponse() {
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<Category> getData() {
        return data;
    }

    public void setData(List<Category> data) {
        this.data = data;
    }

}
