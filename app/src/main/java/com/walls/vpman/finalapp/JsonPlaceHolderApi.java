package com.walls.vpman.finalapp;



import com.walls.vpman.finalapp.Model.WallArray;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi
{
    @GET("api")
    Call<WallArray> getWall(

            @Query("key") String key,
            @Query("q")String query,
            @Query("image_type") String image_type,
            @Query("per_page") int per_page,
            @Query("safesearch") Boolean safesearch
            );
}
