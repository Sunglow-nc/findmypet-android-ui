package com.example.findmypet_android_ui.service;

import android.app.Application;
import android.media.tv.interactive.AppLinkInfo;
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
        Call<List<Poster>> call = posterApiService.getAllAlbums();
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
}
