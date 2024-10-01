package com.example.findmypet_android_ui.ui.addposter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.findmypet_android_ui.model.Owner;
import com.example.findmypet_android_ui.model.Pet;
import com.example.findmypet_android_ui.model.Poster;
import com.example.findmypet_android_ui.ui.home.HomeFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.time.LocalDate;

public class AddPosterClickHandler{
    Poster poster;
    Context context;
    AddPosterViewModel viewModel;

    public AddPosterClickHandler(Poster poster, Context context, AddPosterViewModel viewModel) {
        this.poster = poster;
        this.context = context;
        this.viewModel = viewModel;
    }

}
