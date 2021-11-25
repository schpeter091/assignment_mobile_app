package com.example.assignment.model;

import java.io.Serializable;

public class ReviewData implements Serializable {

    private String restaurantId;
    private String review;
    private String postedBy;
    private String userId;
    private String id;

    public ReviewData() {
    }

    public ReviewData(String restaurantId, String review, String postedBy, String userId) {
        this.restaurantId = restaurantId;
        this.review = review;
        this.postedBy = postedBy;
        this.userId = userId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
