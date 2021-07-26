package com.example.tasty.dataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecipeDao {


    @Insert
    void insertRecipe(Recipe recipe);

    @Delete
    void deleteRecipe(Recipe recipe);

    @Query("select * from Recipe")
    List <Recipe> getAllData();

    @Query("select * from Recipe where id = :id ")
    Recipe getRecipe(int id);

    @Query("select id from Recipe ")
    List<Integer> getAllId();
}
