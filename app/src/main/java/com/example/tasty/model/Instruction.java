package com.example.tasty.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Instruction {

    @SerializedName("display_text")
    @Expose
    private String displayText;

    public String getDisplayText(){
        return displayText;
    }
}
