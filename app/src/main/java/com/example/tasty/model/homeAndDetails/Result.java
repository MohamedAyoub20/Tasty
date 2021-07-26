package com.example.tasty.model.homeAndDetails;

import com.example.tasty.model.Item;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result  {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("items")
    @Expose
    private List<Item> items;

    @SerializedName("item")
    @Expose
    private Item item;

    @SerializedName("display")
    @Expose
    private String display;


    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public List<Item> getItems() {
        return items;
    }

    public Item getItem() {
        return item;
    }

    public String getDisplay(){
        return display;
    }

    @Override
    public String toString() {
        return "Results{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", items=" + items +
                ", item=" + item +
                '}';
    }
}
