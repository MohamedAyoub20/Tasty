package com.example.tasty.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserRating {

    @SerializedName("score")
    @Expose
    private double score;

    public double getScore(){
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
