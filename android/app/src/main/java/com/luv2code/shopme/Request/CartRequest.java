package com.luv2code.shopme.Request;

public class CartRequest {
    Integer id;
    Integer quantity;

    public CartRequest() {
    }

    public CartRequest(Integer id, Integer quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
