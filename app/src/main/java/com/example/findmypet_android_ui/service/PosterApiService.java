package com.example.findmypet_android_ui.service;

import com.example.findmypet_android_ui.model.Poster;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PosterApiService {
    @GET("api/v1/posters")
    Call<List<Poster>> getAllPosters();

    @POST("api/v1/posters")
    Call<Poster> addPoster(@Body Poster poster);

    @DELETE("api/v1/posters/{id}")
    Call<Void> deletePoster(@Path("id") Long id);
}
