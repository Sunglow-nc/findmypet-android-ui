package com.example.findmypet_android_ui.ui.aboutus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.findmypet_android_ui.databinding.FragmentAboutusBinding;

public class AboutUsFragment extends Fragment {

    private FragmentAboutusBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AboutUsViewModel aboutUsViewModel =
                new ViewModelProvider(this).get(AboutUsViewModel.class);

        binding = FragmentAboutusBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textInfo = binding.textAboutus;
        aboutUsViewModel.getText().observe(getViewLifecycleOwner(), textInfo::setText);

        final TextView header = binding.textHeader;
        aboutUsViewModel.getHeaderText().observe(getViewLifecycleOwner(), header::setText);

        final TextView headerWhoAreWe = binding.headerWhoWeAre;
        aboutUsViewModel.getDeveloperHeader().observe(getViewLifecycleOwner(), headerWhoAreWe::setText);

        final TextView textWhoAreWe = binding.textWhoWeAre;
        aboutUsViewModel.getDeveloperDetail().observe(getViewLifecycleOwner(), textWhoAreWe::setText);

        final TextView developerNames = binding.developerNames;
        aboutUsViewModel.getDeveloperNames().observe(getViewLifecycleOwner(), developerNames::setText);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}