package com.luv2code.shopme.Request;

public class ReviewRequest {
    private Integer productId;
    private Integer vote;
    private String comment;

    public ReviewRequest(Integer productId, Integer vote, String comment) {
        this.productId = productId;
        this.vote = vote;
        this.comment = comment;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getVote() {
        return vote;
    }

    public void setVote(Integer vote) {
        this.vote = vote;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
