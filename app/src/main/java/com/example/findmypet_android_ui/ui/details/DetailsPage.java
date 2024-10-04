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

public class DetailsPage extends Fragment {

    private DetailsPageViewModel mViewModel;

    public static DetailsPage newInstance() {
        return new DetailsPage();
    }

    private Context context;
    private Poster poster;
    private Pet pet;
    private Owner owner;
    private Button editButton;
    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentDetailsPageBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details_page, container, false);
        mViewModel = new ViewModelProvider(this).get(DetailsPageViewModel.class);
        view = binding.getRoot();

//        if (getArguments() != null) {
//            FragmentArg
//        }

        assert getArguments() != null;
        poster = getArguments().getParcelable("poster");
        binding.setPoster(poster);

//        assert poster != null;
//        DetailsPageClickHandler clickHandlers = new DetailsPageClickHandler(getContext(), poster, poster.getPet(), poster.getPet().getOwner(), mViewModel);
//        binding.setClickHandler(clickHandlers);
        editButton = view.findViewById(R.id.editButton);
        onEditButtonClicked();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DetailsPageViewModel.class);
        // TODO: Use the ViewModel
    }


    public void onEditButtonClicked() {
//        Intent intent = new Intent(context, EditPosterActivity.class);
//
//        intent.putExtra("poster", poster);
//        context.startActivity(intent);
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

}