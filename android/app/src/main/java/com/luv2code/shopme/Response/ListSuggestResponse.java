package com.luv2code.shopme.Response;

import java.util.List;

public class ListSuggestResponse extends BaseResponse {
    private List<String> data;

    public ListSuggestResponse(List<String> data) {
        this.data = data;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
