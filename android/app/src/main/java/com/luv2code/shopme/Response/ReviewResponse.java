package com.luv2code.shopme.Response;

import com.luv2code.shopme.Model.Review;

public class ReviewResponse extends BaseResponse {
    private Review data;

    public ReviewResponse(Review data) {
        this.data = data;
    }

    public ReviewResponse() {

    }

    public Review getData() {
        return data;
    }

    public void setData(Review data) {
        this.data = data;
    }
}
