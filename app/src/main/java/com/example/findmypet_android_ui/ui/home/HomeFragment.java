package com.example.findmypet_android_ui.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.findmypet_android_ui.MainActivity;
import com.example.findmypet_android_ui.R;
import com.example.findmypet_android_ui.databinding.FragmentHomeBinding;
import com.example.findmypet_android_ui.ui.maps.MapsFragment;
import com.google.android.gms.maps.MapFragment;

public class HomeFragment extends Fragment implements View.OnClickListener{

    private FragmentHomeBinding binding;
    private View view;
    private Button mapViewButton;
    private NavController navController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        mapViewButton = view.findViewById(R.id.mapview_button);
        mapViewButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {
        navController = Navigation.findNavController(view);
        navController.navigate(R.id.action_listView_to_mapview);
    }
}