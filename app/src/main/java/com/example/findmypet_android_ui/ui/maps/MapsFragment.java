package com.example.findmypet_android_ui.ui.maps;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.content.ContextCompat;
import com.example.findmypet_android_ui.R;
import com.example.findmypet_android_ui.databinding.FragmentMapsBinding;
import com.example.findmypet_android_ui.model.Poster;
import com.example.findmypet_android_ui.ui.home.HomeViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class MapsFragment extends Fragment implements View.OnClickListener {

    private View view;
    private Button listViewButton;
    private NavController navController;
    private FragmentMapsBinding binding;
    private List<Poster> posters;
    private MapsViewModel viewModel;
    private GoogleMap map;
    private ActivityResultLauncher<String> requestPermissionLauncher;
    private FusedLocationProviderClient fusedLocationClient;
    private LatLng userLocation;
    private LocationManager locationManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set up location access before view is created

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted) {
                // Permission is granted
                accessUserLocation();
            } else {
                // Permission is denied
                Toast.makeText(getContext(), "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel =
                new ViewModelProvider(this).get(MapsViewModel.class);
        binding = FragmentMapsBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        listViewButton = view.findViewById(R.id.listview_button);
        listViewButton.setOnClickListener(this);
        //check if location permission has been granted
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission();
        } else {
            accessUserLocation();
        }
        getAllPosters();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            map = googleMap;
            LatLng defaultLocation = new LatLng(54.607868, -2.021308);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 6));

            if (userLocation != null) {
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 10));
            }
        }
    };

    @Override
    public void onClick(View view) {
        navController = Navigation.findNavController(view);
        navController.navigate(R.id.action_mapView_to_listview);
    }

    private void getAllPosters(){
        viewModel.getAllPosters().observe(getViewLifecycleOwner(), new Observer<List<Poster>>() {
            @Override
            public void onChanged(List<Poster> postersFromLiveData) {
                posters = postersFromLiveData;

                if (map != null && posters != null && !posters.isEmpty()) {
                    Log.d("MapsFragment", "Number of posters: " + posters.size());
                    for (Poster poster : posters) {
                        try {
                            Log.d("MapsFragment", "Poster location: " + poster.getPet().getLatitude() + ", " + poster.getPet().getLongitude());
                        } catch (NullPointerException e) {
                            Log.e("null exception", "error has occurred when getting longitude and latitude");
                        }
                    }
                    addMarkers();
                } else if (map == null) {
                    // Map is not ready yet, it will add markers when onMapReady is called
                    Log.d("MapsFragment", "Map is not ready, markers will be added when it is.");
                } else {
                    Toast.makeText(getContext(),
                            "Unable to load locations",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addMarkers(){
        if(posters != null) {
            for (Poster poster : posters) {
                LatLng posterLocation = new LatLng(poster.getPet().getLatitude(), poster.getPet().getLongitude());
                map.addMarker(new MarkerOptions().position(posterLocation).title(poster.getTitle()));
            }
        }
    }

    public void requestLocationPermission(){
        requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION);
    }

    public void accessUserLocation(){
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            Location location = task.getResult();
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            userLocation = new LatLng(latitude, longitude);
                            if (map != null) {
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 10));
                            }
                        } else {
                            Toast.makeText(getContext(), "Unable to get location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}