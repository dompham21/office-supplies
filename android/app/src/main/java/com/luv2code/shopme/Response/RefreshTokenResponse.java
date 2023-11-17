package com.luv2code.shopme.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RefreshTokenResponse extends BaseResponse{
    @SerializedName("accessToken")
    @Expose
    private String accessToken;

    @SerializedName("refreshToken")
    @Expose
    private String refreshToken;

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
        return "RefreshTokenResponse{" +
                "result=" + getResult() +
                ", method='" + getMethod() + '\'' +
                ", msg='" + getMsg() + '\'' +
                ", timestamp=" + getTimestamp() +
                ", accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", status='" + status + '\'' +
                ", code=" + getCode() +
                '}';
    }
}
