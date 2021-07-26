package com.example.tasty.model.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TagResult {

    @SerializedName("name")
    @Expose
    private String Name;

    @SerializedName("display_name")
    @Expose
    private String displayName;

    @SerializedName("type")
    @Expose
    private String type;

    public String getName() {
        return Name;
    }

    public String getDisplayName(){
        return displayName;
    }

    public String getType (){
        return type;
    }
}
