package com.example.findmypet_android_ui.ui.updateposter;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.AndroidViewModel;


import com.example.findmypet_android_ui.model.Poster;
import com.example.findmypet_android_ui.service.PosterRepository;

public class UpdatePosterViewModel extends AndroidViewModel {
    private PosterRepository posterRepository;

    public UpdatePosterViewModel(@NonNull Application application) {
        super(application);
        posterRepository = new PosterRepository(application);
    }

    public void updatePoster(Long id, Poster poster){
        posterRepository.updatePoster(id, poster);
    }

    public void deletePoster(Long id){
        posterRepository.deletePoster(id);
    }
}