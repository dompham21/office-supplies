package com.luv2code.shopme.Response;

import com.google.gson.annotations.SerializedName;
import com.luv2code.shopme.Model.Category;
import com.luv2code.shopme.Model.Product;

import java.util.List;

public class ListProductResponse extends BaseResponse{
    @SerializedName("data")
    List<Product> data;

    @SerializedName("totalPage")
    private Integer totalPage;

    @SerializedName("pageNum")
    private Integer pageNum;

    public ListProductResponse() {
    }

    public List<Product> getData() {
        return data;
    }

    public void setData(List<Product> data) {
        this.data = data;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }
}
