package com.example.tasty.model.search;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchRoot {

    @SerializedName("results")
    @Expose
    private List<SearchResult> results = null;

    public List<SearchResult> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "SearchRoot{" +
                "results=" + results +
                '}';
    }
}
