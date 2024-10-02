package com.example.findmypet_android_ui.service;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.findmypet_android_ui.model.Poster;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PosterRepository {
    private MutableLiveData<List<Poster>> mutableLiveData = new MutableLiveData<>();
    private Application application;

    public PosterRepository(Application application){
        this.application = application;
    }

    public MutableLiveData<List<Poster>> getMutableLiveData(){
        PosterApiService posterApiService = RetrofitInstance.getService();
        Call<List<Poster>> call = posterApiService.getAllPosters();
        call.enqueue(new Callback<List<Poster>>() {
            @Override
            public void onResponse(Call<List<Poster>> call, Response<List<Poster>> response) {
                List<Poster> livePosterList = response.body();
                mutableLiveData.setValue(livePosterList);
            }

            @Override
            public void onFailure(Call<List<Poster>> call, Throwable t) {
                Toast.makeText(application.getApplicationContext(),
                        "Unable to get list",
                        Toast.LENGTH_SHORT).show();
            }
        });
        return mutableLiveData;
    }

    public void addPoster(Poster poster){
        PosterApiService posterApiService = RetrofitInstance.getService();
        Call<Poster> call = posterApiService.addPoster(poster);
        call.enqueue(new Callback<Poster>() {
            @Override
            public void onResponse(Call<Poster> call, Response<Poster> response) {
                Toast.makeText(application.getApplicationContext(),
                        "Lost pet has been posted",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Poster> call, Throwable t) {
                Toast.makeText(application.getApplicationContext(),
                        "Unable to add this poster",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deletePoster(Long id){
        PosterApiService posterApiService = RetrofitInstance.getService();
        Call<Void> call = posterApiService.deletePoster(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(application.getApplicationContext(),
                        "Poster successfully deleted",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(application.getApplicationContext(),
                        "Unable to delete poster",
                        Toast.LENGTH_SHORT).show();
                Log.e("DELETE REQUEST", t.getMessage());
            }
        });
    }

    public void updatePoster(Long id, Poster poster){
        PosterApiService posterApiService = RetrofitInstance.getService();
        Call<Poster> call = posterApiService.updatePoster(id, poster);
        call.enqueue(new Callback<Poster>() {
            @Override
            public void onResponse(Call<Poster> call, Response<Poster> response) {
                Toast.makeText(application.getApplicationContext(),
                        "Poster has been updated",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Poster> call, Throwable t) {
                Toast.makeText(application.getApplicationContext(),
                        "Unable to update poster",
                        Toast.LENGTH_SHORT).show();
                Log.e("PUT REQUEST", t.getMessage());
            }
        });
    }
}
