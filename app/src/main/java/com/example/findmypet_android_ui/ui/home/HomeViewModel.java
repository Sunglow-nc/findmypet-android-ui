package com.example.findmypet_android_ui.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.findmypet_android_ui.model.Poster;
import com.example.findmypet_android_ui.service.PosterRepository;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {
    PosterRepository posterRepository;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        this.posterRepository = new PosterRepository(application);
    }

    public LiveData<List<Poster>> getAllPosters(){
        return posterRepository.getMutableLiveData();
    }
}