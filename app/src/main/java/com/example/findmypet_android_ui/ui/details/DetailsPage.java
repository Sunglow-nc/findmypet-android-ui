package com.example.findmypet_android_ui.ui.details;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.findmypet_android_ui.R;
import com.example.findmypet_android_ui.databinding.FragmentDetailsPageBinding;
import com.example.findmypet_android_ui.model.Poster;

public class DetailsPage extends Fragment {

    private DetailsPageViewModel mViewModel;

    public static DetailsPage newInstance() {
        return new DetailsPage();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentDetailsPageBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details_page, container, false);
        mViewModel = new ViewModelProvider(this).get(DetailsPageViewModel.class);

//        if (getArguments() != null) {
//            FragmentArg
//        }

        assert getArguments() != null;
        Poster poster = getArguments().getParcelable("poster");
        binding.setPoster(poster);

        return inflater.inflate(R.layout.fragment_details_page, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DetailsPageViewModel.class);
        // TODO: Use the ViewModel
    }

}