package com.example.findmypet_android_ui.ui.addposter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.findmypet_android_ui.R;
import com.example.findmypet_android_ui.databinding.FragmentAddPosterBinding;
import com.example.findmypet_android_ui.model.Owner;
import com.example.findmypet_android_ui.model.Pet;
import com.example.findmypet_android_ui.model.Poster;
import com.example.findmypet_android_ui.ui.home.HomeFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AddPosterFragment extends Fragment implements OnMapReadyCallback {

    private FragmentAddPosterBinding binding;
    private GoogleMap mapFragment;
    private View view;
    private LatLng selectedLocation;
    private AddPosterClickHandler clickHandler;
    private Poster poster;
    private AddPosterViewModel viewModel;
    private Button submitButton;
    private Pet pet;
    private Owner owner;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(AddPosterViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_poster, container, false);
        view = binding.getRoot();
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        poster = new Poster();
        pet = new Pet();
        owner = new Owner();

//        clickHandler = new AddPosterClickHandler(poster, getContext(), viewModel);
        binding.setPoster(poster);
        binding.setPet(pet);
        binding.setOwner(owner);

        submitButton = view.findViewById((R.id.button));
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(poster.getDescription() == null || poster.getTitle() == null
                        || pet.getColour() == null || pet.getAge() == null
//                        || pet.getLostDate() == null
                        || pet.getType() == null
                        || owner.getName() == null || owner.getEmailAddress() == null
                        || owner.getContactNumber() == null){
                    Toast.makeText(getContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    owner.setId(null);
                    Pet newPet = new Pet(null, pet.getName(), pet.getColour(), Long.parseLong(pet.getAge()),
                            false, selectedLocation.longitude, selectedLocation.latitude, pet.getImageURL(),
                            pet.getLostDate(), pet.getType(), owner);
                    Poster newPoster = new Poster(null, LocalDate.now().toString(),
                            poster.getDescription(), poster.getTitle(), newPet);
                    viewModel.addPoster(newPoster);
                    getContext().startActivity(intent);
                }
            }
        });
//        binding.setClickHandler(clickHandler);
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

//        clickHandler.mapLocation(mapFragment);

        mapFragment.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                // Clear prev markers
                mapFragment.clear();

                // Add marker where user clicks
                mapFragment.addMarker(new MarkerOptions().position(latLng).title("Selected Location"));

                // Save the marker
                try {
                    selectedLocation = latLng;
                } catch (NullPointerException e){
                    Log.e("error", "error occurred when trying to save location");
                }
            }
        });
    }

}