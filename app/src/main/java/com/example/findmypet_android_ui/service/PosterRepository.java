package com.example.findmypet_android_ui.service;

import android.app.Application;
import android.media.tv.interactive.AppLinkInfo;

import androidx.lifecycle.MutableLiveData;

import com.example.findmypet_android_ui.model.Poster;

import java.util.List;

public class PosterRepository {
    private MutableLiveData<List<Poster>> mutableLiveData = new MutableLiveData<>();
    private Application application;

    public PosterRepository(Application application){
        this.application = application;
    }
}
