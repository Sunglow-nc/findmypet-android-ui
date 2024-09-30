package com.example.findmypet_android_ui.ui.maps;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.findmypet_android_ui.service.PosterRepository;

public class MapsViewModel extends AndroidViewModel {
    PosterRepository posterRepository;

    public MapsViewModel(@NonNull Application application) {
        super(application);
        this.posterRepository = new PosterRepository(application);
    }
}
