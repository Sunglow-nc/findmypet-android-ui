package com.example.findmypet_android_ui.ui.addposter;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.findmypet_android_ui.model.Poster;
import com.example.findmypet_android_ui.service.PosterRepository;

public class AddPosterViewModel extends AndroidViewModel {
    PosterRepository posterRepository;

    public AddPosterViewModel(@NonNull Application application) {
        super(application);
        this.posterRepository = new PosterRepository(application);
    }

    public void addPoster(Poster poster){
        posterRepository.addPoster(poster);
    }
}