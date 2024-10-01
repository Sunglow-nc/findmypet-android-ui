package com.example.findmypet_android_ui.ui.addposter;

import android.content.Context;

import com.example.findmypet_android_ui.model.Poster;

public class AddPosterClickHandler {
    Poster poster;
    Context context;
    AddPosterViewModel viewModel;

    public AddPosterClickHandler(Poster poster, Context context, AddPosterViewModel viewModel) {
        this.poster = poster;
        this.context = context;
        this.viewModel = viewModel;
    }
}
