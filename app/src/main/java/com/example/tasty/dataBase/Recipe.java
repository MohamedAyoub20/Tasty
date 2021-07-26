package com.example.tasty.dataBase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


import com.example.tasty.model.Instruction;
import com.example.tasty.model.Nutrition;
import com.example.tasty.model.Section;
import com.example.tasty.model.UserRating;

import java.util.List;

@Entity
public class Recipe {

    @PrimaryKey
    private int id;

    private String name;

    private String slug;

    private String thumbnailUrl;
/*
    private UserRating userRating;

    private String videoUrl;

    private int numberServings;

    private int prepTime;

    private int cookTime;

    private List<Section> sections = null;

    private Nutrition nutrition;

    private List<Instruction> instructions = null;*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
/*
    public UserRating getUserRating() {
        return userRating;
    }

    public void setUserRating(UserRating userRating) {
        this.userRating = userRating;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public int getNumberServings() {
        return numberServings;
    }

    public void setNumberServings(int numberServings) {
        this.numberServings = numberServings;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public int getCookTime() {
        return cookTime;
    }

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public Nutrition getNutrition() {
        return nutrition;
    }

    public void setNutrition(Nutrition nutrition) {
        this.nutrition = nutrition;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
    }*/
}
