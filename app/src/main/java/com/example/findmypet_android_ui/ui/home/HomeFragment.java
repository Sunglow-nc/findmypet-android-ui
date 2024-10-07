package com.example.findmypet_android_ui.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findmypet_android_ui.R;
import com.example.findmypet_android_ui.databinding.FragmentHomeBinding;
import com.example.findmypet_android_ui.model.Poster;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener, RecyclerViewInterface, AdapterView.OnItemSelectedListener {

    private FragmentHomeBinding binding;
    private View view;
    private Button mapViewButton;
    private NavController navController;
    private HomeViewModel viewModel;
    private List<Poster> posters;
    private RecyclerView recyclerView;
    private PosterAdapter posterAdapter;
    private Spinner spinner;
    private String spinnerSelection;
    private SearchView searchView;
    private ArrayList<Poster> filteredList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        view = binding.getRoot();

        mapViewButton = view.findViewById(R.id.mapview_button);
        mapViewButton.setOnClickListener(this);

        spinner = view.findViewById(R.id.dropdown);
        spinner.setOnItemSelectedListener(this);
        String[] dropItems = getResources().getStringArray(R.array.filter_items);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, dropItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        searchView = view.findViewById(R.id.search_bar);
        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterList(s);
                return true;
            }
        });

        searchView.setOnQueryTextFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                searchView.setIconified(false);
            }
        });

        searchView.setOnClickListener(v -> {
            searchView.setIconified(false);
            searchView.requestFocus();
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(searchView, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        getAllPosters();
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

    private void getAllPosters(){
        viewModel.getAllPosters().observe(getViewLifecycleOwner(), new Observer<List<Poster>>() {
            @Override
            public void onChanged(List<Poster> postersFromLiveData) {
                posters = postersFromLiveData;
                displayInRecyclerView();
            }
        });
    }

    private void displayInRecyclerView(){
        recyclerView = binding.recyclerView;
        posterAdapter = new PosterAdapter(this, this.getContext(), posters);
        recyclerView.setAdapter(posterAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        posterAdapter.notifyDataSetChanged();
    }

    private void filterList(String s) {
        filteredList = new ArrayList<>();
        for (Poster poster : posters) {
            switch (spinnerSelection) {
                case "All": {
                    if (poster.getDatePosted().toLowerCase().contains(s.toLowerCase()) ||
                            poster.getDescription().toLowerCase().contains(s.toLowerCase()) ||
                            poster.getTitle().toLowerCase().contains(s.toLowerCase()) ||
                            poster.getPet().getName().toLowerCase().contains(s.toLowerCase()))
                    {
                        if (!filteredList.contains(poster)) {
                            filteredList.add(poster);
                        }
                    }
                    break;
                }
                case "Colour": {
                    if (poster.getPet().getColour().toLowerCase().contains(s.toLowerCase())) {
                        if (!filteredList.contains(poster)) {
                            filteredList.add(poster);
                        }
                    }
                    break;
                }
                case "Type": {
                    if (poster.getPet().getType().toLowerCase().contains(s.toLowerCase())) {
                        if (!filteredList.contains(poster)) {
                            filteredList.add(poster);
                        }
                    }
                    break;
                }
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(view.getContext(), "No posters found", Toast.LENGTH_SHORT).show();
        }
        posterAdapter.setFilteredList(filteredList);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerSelection = parent.getItemAtPosition(position).toString();
        Toast.makeText(getContext(), "Selected: " + spinnerSelection, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onItemClick(int position) {
//        Intent intent = new Intent(getActivity(), PosterDetailsActivity.class);
//        intent.putExtra("title", poster.getTitle());
//        intent.putExtra("petName", poster.getPet().getName());
//        intent.putExtra("contactNumber", poster.getPet().getOwner().getContactNumber());
//        intent.putExtra("type", poster.getPet().getType());
//        intent.putExtra("lostDate", poster.getPet().getLostDate());

//        startActivity(intent);
//        HomeFragmentDirections.ActionDetailPage action = HomeFragmentDirections.actionDetailPage(poster);

        Bundle bundle = new Bundle();
        if(filteredList == null || filteredList.isEmpty()){
            bundle.putParcelable("poster", posters.get(position));
        } else {
            bundle.putParcelable("poster", filteredList.get(position));
            spinner.setSelection(0);
            filteredList = null;
        }
        navController = Navigation.findNavController(view);
        navController.navigate(R.id.action_detail_page, bundle);
    }

}