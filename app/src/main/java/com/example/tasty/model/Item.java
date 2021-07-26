package com.example.tasty.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Item {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("slug")
    @Expose
    private String slug;

    @SerializedName("thumbnail_url")
    @Expose
    private String thumbnailUrl;

    @SerializedName("user_ratings")
    @Expose
    private UserRating userRating;

    @SerializedName("video_url")
    @Expose
    private String videoUrl;

    @SerializedName("num_servings")
    @Expose
    private int numberServings;

    @SerializedName("prep_time_minutes")
    @Expose
    private int prepTime;

    @SerializedName("cook_time_minutes")
    @Expose
    private int cookTime;

    @SerializedName("sections")
    @Expose
    private List<Section> sections = null;

    @SerializedName("nutrition")
    @Expose
    private Nutrition nutrition;

    @SerializedName("instructions")
    @Expose
    private List<Instruction> instructions = null;

    public int getId(){
    return id;
    }

    public String getName() {
        return name;
    }

    public String getSlug(){
        return slug;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public UserRating getUserRating() {
        return userRating;
    }

    public String getVideoUrl(){
        return videoUrl;
    }

    public int getNumberServings(){
        return numberServings;
    }

    public int getPrepTime(){
        return prepTime;
    }

    public int getCookTime(){
        return cookTime;
    }

    public List<Section> getSections() {
        return sections;
    }

    public Nutrition getNutrition() {
        return nutrition;
    }

    public List<Instruction>  getInstructions(){
        return instructions;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
