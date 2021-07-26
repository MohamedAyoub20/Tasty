package com.example.tasty.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TotalTimeTier {

    @SerializedName("display_tier")
    @Expose
    private String displayTier;


    public String getDisplayTier() {
        return displayTier;
    }
}
