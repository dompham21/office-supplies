package com.luv2code.shopme.Response;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {
    @SerializedName("result")
    int result;

    @SerializedName("status")
    String status;

    @SerializedName("msg")
    private String msg;

    @SerializedName("method")
    private String method;

    @SerializedName("timestamp")
    private long timestamp;

    @SerializedName("code")
    private Integer code;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
