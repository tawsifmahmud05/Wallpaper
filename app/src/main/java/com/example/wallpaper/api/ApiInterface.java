package com.example.wallpaper.api;

import com.example.wallpaper.model.ImageModel;
import com.example.wallpaper.model.SearchModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

import static com.example.wallpaper.api.ApiUtilities.API_KEY;

public interface ApiInterface {

    @Headers("Authorization: Client-ID "+API_KEY)
    @GET("/photos/")
    Call<List<ImageModel>> getImages(
        @Query("page") int page,
        @Query("per_page") int perPage
    );

    @Headers("Authorization: Client-ID "+API_KEY)
    @GET("/search/photos/")
    Call<SearchModel> searchImages(
            @Query("query") String query

    );
}
