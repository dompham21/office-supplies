package com.luv2code.shopme.Model;

import java.io.Serializable;
import java.util.Date;

public class Review implements Serializable {
    private Integer id;

    private User user;

    private Product product;

    private Date date;

    private String comment;

    private Integer vote;

    public Review(Integer id, User user, Product product, Date date, String comment, Integer vote) {
        this.id = id;
        this.user = user;
        this.product = product;
        this.date = date;
        this.comment = comment;
        this.vote = vote;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getVote() {
        return vote;
    }

    public void setVote(Integer vote) {
        this.vote = vote;
    }
}
