package com.example.assignment.model;

import java.io.Serializable;

public class ReviewData implements Serializable {

    private String restaurantId;
    private String review;
    private String postedBy;

    public ReviewData(String restaurantId, String review, String postedBy) {
        this.restaurantId = restaurantId;
        this.review = review;
        this.postedBy = postedBy;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }
}
