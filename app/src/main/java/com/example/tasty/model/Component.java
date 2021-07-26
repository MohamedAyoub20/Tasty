package com.example.tasty.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Component {

    @SerializedName("raw_text")
    @Expose
    private String rawText;

    public String getRawText(){
        return rawText;
    }
}
