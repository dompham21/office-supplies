package com.luv2code.shopme.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.luv2code.shopme.Model.User;

public class LoginResponse extends BaseResponse{
    @SerializedName("data")
    User data;

    @SerializedName("accessToken")
    @Expose
    private String accessToken;

    @SerializedName("refreshToken")
    @Expose
    private String refreshToken;

    public LoginResponse() {
    }



    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                ", result=" + result +
                ", status='" + status + '\'' +
                ", msg='" + getMsg() + '\'' +
                ", method='" + getMethod() + '\'' +
                ", timestamp=" + getTimestamp() +
                ", code=" + getCode() +
                '}';
    }
}
