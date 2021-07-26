package com.example.tasty.model.search;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class SearchResult {


    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("thumbnail_url")
    @Expose
    private String thumbnailUrl;

    @SerializedName("display")
    @Expose
    private String displayAutoComp;



    public int getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getDisplayAutoComp(){
        return displayAutoComp;
    }


}
