package com.example.findmypet_android_ui.ui.details;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.findmypet_android_ui.R;
import com.example.findmypet_android_ui.databinding.FragmentDetailsPageBinding;
import com.example.findmypet_android_ui.model.Owner;
import com.example.findmypet_android_ui.model.Pet;
import com.example.findmypet_android_ui.model.Poster;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DetailsPage extends Fragment implements OnMapReadyCallback {

    private DetailsPageViewModel mViewModel;

    public static DetailsPage newInstance() {
        return new DetailsPage();
    }

    private Context context;
    private Poster poster;
    private Button editButton;
    private View view;
    private GoogleMap mapFragment;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentDetailsPageBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details_page, container, false);
        mViewModel = new ViewModelProvider(this).get(DetailsPageViewModel.class);
        view = binding.getRoot();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        assert getArguments() != null;
        poster = getArguments().getParcelable("poster");
        binding.setPoster(poster);

        editButton = view.findViewById(R.id.editButton);
        onEditButtonClicked();

        return view;
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(DetailsPageViewModel.class);
//        // TODO: Use the ViewModel
//    }


    public void onEditButtonClicked() {
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putParcelable("poster", poster);
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.nav_update_poster, bundle);
            }
        });

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mapFragment = googleMap;
        LatLng location = new LatLng(poster.getPet().getLatitude(), poster.getPet().getLongitude());
        mapFragment.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 14));
        mapFragment.addMarker(new MarkerOptions().position(location).title(poster.getPet().getName()));
    }
}