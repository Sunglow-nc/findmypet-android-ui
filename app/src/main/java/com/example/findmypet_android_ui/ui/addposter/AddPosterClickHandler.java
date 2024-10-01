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

    public void submitButton(View view){
        if(poster.getDescription() == null || poster.getTitle() == null || poster.getPet().getName() == null
                || poster.getPet().getColour() == null || poster.getPet().getAge() == null
                || poster.getPet().getLostDate() == null || poster.getPet().getType() == null
                || poster.getPet().getOwner().getName() == null || poster.getPet().getOwner().getEmailAddress() == null
                || poster.getPet().getOwner().getContactNumber() == null){
            Toast.makeText(context, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(context, HomeFragment.class);
            viewModel.addPoster(poster);
            context.startActivity(intent);
        }
    }

    public void mapLocation(GoogleMap mapFragment) {

        mapFragment.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                // Clear prev markers
                mapFragment.clear();

                // Add marker where user clicks
                mapFragment.addMarker(new MarkerOptions().position(latLng).title("Selected Location"));

                // Save the marker
                poster.getPet().setLatitude(latLng.latitude);
                poster.getPet().setLongitude(latLng.longitude);
            }
        });
    }
}
