package com.example.tasty.model.homeAndDetails;


import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Root {

    @SerializedName("results")
    @Expose
    private List<Result> results = null;

    public List<Result> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "Root{" +
                "results=" + results +
                '}';
    }
}
