package com.luv2code.shopme.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.luv2code.shopme.Model.User;

import java.util.List;

public class BuyHistoryResponse extends BaseResponse{
    private List<Double> data;
    private List<String> label;

    public BuyHistoryResponse() {
    }

    public List<Double> getData() {
        return data;
    }

    public void setData(List<Double> data) {
        this.data = data;
    }

    public List<String> getLabel() {
        return label;
    }

    public void setLabel(List<String> label) {
        this.label = label;
    }
}
