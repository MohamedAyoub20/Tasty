package com.example.tasty.dataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities={Recipe.class},version = 1)
public abstract class RecipeDataBase extends RoomDatabase {

    public abstract RecipeDao getRecipeDao();


    private static RecipeDataBase object;

    public static RecipeDataBase getAppDatabase(Context context){
        if(object==null){
            synchronized (RecipeDataBase.class){
                if(object==null){
                    object= Room.databaseBuilder(context, RecipeDataBase.class,"Item.db").build();
                }
            }
        }
        return object;
    }

}
