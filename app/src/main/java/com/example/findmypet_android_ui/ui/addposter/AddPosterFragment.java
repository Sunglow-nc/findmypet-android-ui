package com.example.findmypet_android_ui.ui.addposter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.findmypet_android_ui.databinding.FragmentAddPosterBinding;

public class AddPosterFragment extends Fragment {

    private FragmentAddPosterBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AddPosterViewModel addPosterViewModel =
                new ViewModelProvider(this).get(AddPosterViewModel.class);

        binding = FragmentAddPosterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}