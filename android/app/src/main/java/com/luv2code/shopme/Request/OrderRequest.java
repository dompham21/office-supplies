package com.luv2code.shopme.Request;

import com.luv2code.shopme.Model.Address;
import com.luv2code.shopme.Model.Cart;
import com.luv2code.shopme.Model.User;

import java.util.Date;
import java.util.List;

public class OrderRequest {
    private Double totalPrice;
    private Integer addressId;
    private List<Cart> listCart;

    public OrderRequest(Double totalPrice, Integer addressId) {
        this.totalPrice = totalPrice;
        this.addressId = addressId;
    }

    public OrderRequest(Double totalPrice, Integer addressId, List<Cart> listCart) {
        this.totalPrice = totalPrice;
        this.addressId = addressId;
        this.listCart = listCart;
    }

    public List<Cart> getListCart() {
        return listCart;
    }

    public void setListCart(List<Cart> listCart) {
        this.listCart = listCart;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

}
