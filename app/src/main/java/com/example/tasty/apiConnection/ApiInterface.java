package com.example.tasty.apiConnection;




import com.example.tasty.model.Item;
import com.example.tasty.model.homeAndDetails.Root;
import com.example.tasty.model.search.SearchRoot;
import com.example.tasty.model.search.TagRoot;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ApiInterface {


    @GET("feeds/list")
    Call<Root>getAllData(@Header("x-rapidapi-key") String key);

    @GET("recipes/detail")
    Call<Item>getItem(@Header("x-rapidapi-key") String key, @Query("id")int id);

    @GET("recipes/auto-complete")
    Call<SearchRoot> getAllAutoCompData(@Header("x-rapidapi-key") String key, @Query("prefix") String prefix);

    @GET("tags/list")
    Call<TagRoot> getAllTags(@Header("x-rapidapi-key") String key);

    @GET("recipes/list")
    Call<SearchRoot> getSearchDataByQuery(@Header("x-rapidapi-key") String key, @Query("q") String searchText);

    @GET("recipes/list")
    Call<SearchRoot> getSearchDataByTag(@Header("x-rapidapi-key") String key, @Query("tags") String tag);

    @GET("recipes/list")
    Call<SearchRoot> getSearchDataByQueryAndTag(@Header("x-rapidapi-key") String key, @Query("q") String searchText, @Query("tags") String tag);

}
