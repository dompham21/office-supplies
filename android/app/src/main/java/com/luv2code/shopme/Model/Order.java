package com.luv2code.shopme.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order implements Serializable {
    private Integer id;
    private Date date;
    private Double totalPrice;
    private OrderStatus status;
    private Address address;
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public Order() {
    }

    public Order(Integer id, Date date, Double totalPrice, OrderStatus status, Address address, List<OrderDetail> orderDetails) {
        this.id = id;
        this.date = date;
        this.totalPrice = totalPrice;
        this.status = status;
        this.address = address;
        this.orderDetails = orderDetails;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
