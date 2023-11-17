package com.luv2code.shopme.Model;

import java.io.Serializable;

public class OrderDetail implements Serializable {
    private Integer id;
    private Integer quantity;
    private Double unitPrice;
    private Product product;
    private Boolean hasReviewed;

    public OrderDetail(Integer id, Integer quantity, Double unitPrice, Product product) {
        this.id = id;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.product = product;
    }

    public OrderDetail(Integer id, Integer quantity, Double unitPrice, Product product, Boolean hasReviewed) {
        this.id = id;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.product = product;
        this.hasReviewed = hasReviewed;
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

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Boolean getHasReviewed() {
        return hasReviewed;
    }

    public void setHasReviewed(Boolean hasReviewed) {
        this.hasReviewed = hasReviewed;
    }
}
