package com.example.findmypet_android_ui.ui.updateposter;

import static com.example.findmypet_android_ui.ui.util.DataValidation.isUKNumber;
import static com.example.findmypet_android_ui.ui.util.DataValidation.isValidEmail;
import static com.example.findmypet_android_ui.ui.util.DataValidation.isValidName;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.findmypet_android_ui.R;
import com.example.findmypet_android_ui.model.Owner;
import com.example.findmypet_android_ui.model.Pet;
import com.example.findmypet_android_ui.model.Poster;
import com.example.findmypet_android_ui.ui.addposter.AddPosterViewModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.time.LocalDate;

public class UpdatePosterClickHandlers {
    private Poster poster;
    private Owner owner;
    private Pet pet;
    private Context context;
    private UpdatePosterViewModel viewModel;
    ActivityResultLauncher<Intent> imagePickerLauncher;
    private NavController navController;
    private LatLng selectedLocation;
    private long posterId;

    public UpdatePosterClickHandlers(Poster poster, Pet pet, Owner owner, Context context, UpdatePosterViewModel viewModel){
        this.poster = poster;
        this.pet = pet;
        this.owner = owner;
        this.context = context;
        this.viewModel = viewModel;
    }

    public void onSaveButtonClicked(View view) {
        Owner updatedOwner = new Owner(
                owner.getId(),
                owner.getName(),
                owner.getContactNumber(),
                owner.getEmailAddress()
        );
        Pet updatedPet = new Pet(
                pet.getId(),
                pet.getName(),
                pet.getColour(),
                Long.parseLong(pet.getAge()),
                pet.getFound(),
                pet.getLongitude(),
                pet.getLatitude(),
                pet.getImageURL(),
                pet.getLostDate(),
                pet.getType(),
                updatedOwner
        );
        Poster updatedPoser = new Poster(
                poster.getId(),
                poster.getDatePosted(),
                poster.getDescription(),
                poster.getTitle(),
                updatedPet
        );
        if (poster.getTitle() == null || poster.getTitle().length() < 5) {
            Toast.makeText(context, "Title must be 5 characters or more", Toast.LENGTH_SHORT).show();

        } if (owner.getContactNumber() == null || !isUKNumber(owner.getContactNumber())) {
            Toast.makeText(context, "Please enter a valid UK phone number", Toast.LENGTH_SHORT).show();

        } else if (owner.getEmailAddress() == null || !isValidEmail(owner.getEmailAddress())) {
            Toast.makeText(context, "Please enter a valid email address", Toast.LENGTH_SHORT).show();

        } else if (owner.getName() == null || !isValidName(owner.getName())) {
            Toast.makeText(context, "Name must be 2 characters or more", Toast.LENGTH_SHORT).show();

        } else if (selectedLocation == null) {
            Toast.makeText(context, "Please select the location your pet was last seen", Toast.LENGTH_SHORT).show();

        } else if (pet.getImageURL() == null || pet.getImageURL().isEmpty()) {
            Toast.makeText(context, "Please upload an image", Toast.LENGTH_SHORT).show();

        } else if (poster.getDescription() == null || pet.getColour() == null || pet.getAge() == null
                || pet.getLostDate() == null || pet.getType() == null || pet.getName() == null || pet.getImageURL()==null) {
            Toast.makeText(context, "Fields cannot be empty", Toast.LENGTH_SHORT).show();

        } else {
            owner.setId(null);

            posterId = poster.getId();
            viewModel.updatePoster(posterId, updatedPoser);

            Toast.makeText(context, "Poster updated successfully!", Toast.LENGTH_SHORT).show();
            navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_update_to_home);        }
    }

    public void onDeleteButtonClicked(View view) {
        new AlertDialog.Builder(context)
                .setTitle("Confirm Deletion")
                .setMessage("Are you sure you want to delete this post?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    posterId = poster.getId();
                    viewModel.deletePoster(posterId);
                    Toast.makeText(context, "Post deleted successfully!", Toast.LENGTH_SHORT).show();

                    navController = Navigation.findNavController(view);
                    navController.navigate(R.id.action_update_to_home);
                })
                .setNegativeButton("No", (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }

    public void onBackFABClicked(View view){
        navController = Navigation.findNavController(view);
        navController.navigate(R.id.action_update_to_home);
    }

    public void setImagePickerLauncher(ActivityResultLauncher<Intent> imagePickerLauncher) {
        this.imagePickerLauncher = imagePickerLauncher;
    }

    public void onUploadImageButtonClicked(View view) {
        if (imagePickerLauncher != null) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            String[] mimeTypes = {"image/jpeg", "image/png"};
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            imagePickerLauncher.launch(intent);
        } else {
            Toast.makeText(context, "Image picker not initialized", Toast.LENGTH_SHORT).show();
        }
    }

    public void mapClick (GoogleMap mapFragment){
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
                } catch (NullPointerException e) {
                    Log.e("error", "error occurred when trying to save location");
                }
            }
        });
    }
}
