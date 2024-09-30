package com.example.findmypet_android_ui.ui.addposter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.findmypet_android_ui.R;
import com.example.findmypet_android_ui.databinding.FragmentAddPosterBinding;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AddPosterFragment extends Fragment implements OnMapReadyCallback {

    private FragmentAddPosterBinding binding;
    private GoogleMap mapFragment;
    private View view;
    private LatLng selectedLocation;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        AddPosterViewModel addPosterViewModel =
                new ViewModelProvider(this).get(AddPosterViewModel.class);


        binding = FragmentAddPosterBinding.inflate(inflater, container, false);
        view = binding.getRoot();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapFragment = googleMap;

        // TODO: nice to have: set default location - users current location IF location permission set up
        // LatLng defaultLocation = new LatLng(-34, 151);
        // mapFragment.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10));

        mapFragment.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                // Clear prev markers
                mapFragment.clear();

                // Add marker where user clicks
                mapFragment.addMarker(new MarkerOptions().position(latLng).title("Selected Location"));

                // Save the marker
                selectedLocation = latLng;
            }
        });
    }

}