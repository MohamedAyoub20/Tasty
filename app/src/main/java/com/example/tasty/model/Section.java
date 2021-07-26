package com.example.tasty.model;

import com.example.tasty.model.Component;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Section {


    @SerializedName("components")
    @Expose
    private List<Component> components = null;


    public List<Component> getComponents() {
        return components;
    }
}
