package com.example.findmypet_android_ui.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.findmypet_android_ui.service.PosterRepository;

public class HomeViewModel extends AndroidViewModel {
    PosterRepository posterRepository;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        this.posterRepository = new PosterRepository(application);
    }
}