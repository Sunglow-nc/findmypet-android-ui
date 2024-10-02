package com.example.findmypet_android_ui.ui.updateposter;

import androidx.lifecycle.ViewModel;

import com.example.findmypet_android_ui.model.Poster;
import com.example.findmypet_android_ui.service.PosterRepository;

public class UpdatePosterViewModel extends ViewModel {
    PosterRepository posterRepository;

    public void updatePoster(Long id, Poster poster){
        posterRepository.updatePoster(id, poster);
    }
}