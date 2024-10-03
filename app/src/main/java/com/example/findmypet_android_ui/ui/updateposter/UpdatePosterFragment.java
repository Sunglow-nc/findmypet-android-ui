package com.example.findmypet_android_ui.ui.updateposter;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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
import com.google.android.gms.maps.model.MarkerOptions;

import java.time.LocalDate;

public class UpdatePosterFragment extends Fragment implements OnMapReadyCallback {

    private UpdatePosterViewModel viewModel;
    private FragmentAddPosterBinding binding;
    private View view;
    private Poster poster;
    private Pet pet;
    private Owner owner;
    private GoogleMap mapFragment;
    private LatLng selectedLocation;
    private Button saveButton;
    private Button deleteButton;
    private NavController navController;

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

        //need to create a Bundle and set the poster: bundle.putParcelable("poster", poster);
        // then  NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        // navController.navigate(R.id.nav_add_poster, bundle);
        poster = UpdatePosterFragmentArgs.fromBundle(getArguments()).getPoster();
        pet = poster.getPet();
        owner = pet.getOwner();

        binding.setPoster(poster);
        binding.setPet(pet);
        binding.setOwner(owner);

        saveButton = view.findViewById(R.id.saveButton);
        deleteButton = view.findViewById(R.id.deleteButton);

        onClickSaveButton();
        onClickDeleteButton();

        return view;

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
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
                try {
                    selectedLocation = latLng;
                } catch (NullPointerException e){
                    Log.e("error", "error occurred when trying to save location");
                }
            }
        });
    }

    public void onClickSaveButton(){
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(poster.getDescription() == null || poster.getTitle() == null
                        || pet.getColour() == null || pet.getAge() == null
                        || pet.getLostDate() == null || pet.getType() == null
                        || owner.getName() == null || owner.getEmailAddress() == null
                        || owner.getContactNumber() == null){
                    Toast.makeText(getContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    owner.setId(null);
                    Pet newPet = new Pet(null, pet.getName(), pet.getColour(), Long.parseLong(pet.getAge()),
                            false, selectedLocation.longitude, selectedLocation.latitude, pet.getImageURL(),
                            pet.getLostDate(), pet.getType(), owner);
                    Poster newPoster = new Poster(null, poster.getDatePosted(),
                            poster.getDescription(), poster.getTitle(), newPet);
                    viewModel.updatePoster(newPoster.getId(), newPoster);
                    navController = Navigation.findNavController(view);
                    // TODO create action update poster to poster detail view:
                    //  navController.navigate(R.id.action_update_poster_to_home);
                }
            }
        });
    }

    public void onClickDeleteButton(){
        new AlertDialog.Builder(getContext())
                .setTitle("Delete poster")
                .setMessage("Are you sure you want to delete this poster?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // TODO create action update poster to poster detail view:
                        //  navController.navigate(R.id.action_update_poster_to_home);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}