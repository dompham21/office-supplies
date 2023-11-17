package com.luv2code.shopme.Response;

import com.google.gson.annotations.SerializedName;
import com.luv2code.shopme.Model.User;

public class UserResponse extends BaseResponse {
    @SerializedName("data")
    private User data;

    public UserResponse() {
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
