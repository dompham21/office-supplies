package com.luv2code.shopme.Response;

import com.google.gson.annotations.SerializedName;
import com.luv2code.shopme.Model.Product;
import com.luv2code.shopme.Model.Review;

import java.util.List;

public class ListReviewResponse extends BaseResponse {
    @SerializedName("data")
    List<Review> data;

    @SerializedName("totalPage")
    private Integer totalPage;

    @SerializedName("pageNum")
    private Integer pageNum;

    public ListReviewResponse() {
    }

    public List<Review> getData() {
        return data;
    }

    public void setData(List<Review> data) {
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
