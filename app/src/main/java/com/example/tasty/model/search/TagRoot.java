package com.example.tasty.model.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TagRoot {

    @SerializedName("results")
    @Expose
    private List<TagResult> results = null;

    public List<TagResult> getResults() {
        return results;
    }
}
