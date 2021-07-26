package com.example.tasty.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Nutrition {


    @SerializedName("calories")
    @Expose
    private int calories;

    @SerializedName("carbohydrates")
    @Expose
    private int carbohydrates;

    @SerializedName("fat")
    @Expose
    private int fat;

    @SerializedName("protein")
    @Expose
    private int protein;

    @SerializedName("sugar")
    @Expose
    private int sugar;

    @SerializedName("fiber")
    @Expose
    private int fiber;


    public int getCalories() {
        return calories;
    }

    public int getCarbohydrates() {
        return carbohydrates;
    }

    public int getFat() {
        return fat;
    }

    public int getProtein() {
        return protein;
    }

    public int getSugar() {
        return sugar;
    }

    public int getFiber() {
        return fiber;
    }
}
