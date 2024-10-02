package com.example.findmypet_android_ui.ui.updateposter;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.findmypet_android_ui.R;
import com.example.findmypet_android_ui.databinding.FragmentAddPosterBinding;
import com.example.findmypet_android_ui.model.Owner;
import com.example.findmypet_android_ui.model.Pet;
import com.example.findmypet_android_ui.model.Poster;
import com.example.findmypet_android_ui.ui.home.HomeViewModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class UpdatePosterFragment extends Fragment implements OnMapReadyCallback {

    private UpdatePosterViewModel viewModel;
    private FragmentAddPosterBinding binding;
    private View view;
    private Poster poster;
    private Pet pet;
    private Owner owner;
    private GoogleMap mapFragment;
    private LatLng selectedLocation;
    private Button updateButton;
    private Button deleteButton;

    public static UpdatePosterFragment newInstance() {
        return new UpdatePosterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(UpdatePosterViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_update_poster, container, false);
        view = binding.getRoot();
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        poster = new Poster();
        pet = new Pet();
        owner = new Owner();

        binding.setPoster(poster);
        binding.setPet(pet);
        binding.setOwner(owner);
        return view;

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

    }
}