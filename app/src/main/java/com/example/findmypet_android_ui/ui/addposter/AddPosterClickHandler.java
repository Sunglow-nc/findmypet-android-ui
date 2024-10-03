package com.example.findmypet_android_ui.ui.addposter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.findmypet_android_ui.R;
import com.example.findmypet_android_ui.model.Owner;
import com.example.findmypet_android_ui.model.Pet;
import com.example.findmypet_android_ui.model.Poster;
import com.example.findmypet_android_ui.ui.home.HomeFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.time.LocalDate;

public class AddPosterClickHandler{
    Poster poster;
    private Pet pet;
    private Owner owner;
    Context context;
    AddPosterViewModel viewModel;
    ActivityResultLauncher<Intent> imagePickerLauncher;
    private NavController navController;

    public AddPosterClickHandler(Poster poster, Pet pet, Owner owner, Context context, AddPosterViewModel viewModel) {
        this.poster = poster;
        this.pet = pet;
        this.owner = owner;
        this.context = context;
        this.viewModel = viewModel;
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

    public void onSubmitButtonClicked(View view) {
        if (poster.getDescription() == null || poster.getTitle() == null
                || pet.getColour() == null || pet.getAge() == null
                || pet.getLostDate() == null || pet.getType() == null
                || owner.getName() == null || owner.getEmailAddress() == null
                || owner.getContactNumber() == null) {
            Toast.makeText(context, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
        } else {

            owner.setId(null);
            Pet newPet = new Pet(null, pet.getName(), pet.getColour(), Long.parseLong(pet.getAge()),
                    false, pet.getLongitude(), pet.getLatitude(), pet.getImageURL(),
                    pet.getLostDate(), pet.getType(), owner);
            Poster newPoster = new Poster(null, LocalDate.now().toString(),
                    poster.getDescription(), poster.getTitle(), newPet);
            viewModel.addPoster(newPoster);

            Toast.makeText(context, "Poster submitted successfully!", Toast.LENGTH_SHORT).show();
            navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_add_poster_to_home);
        }
    }



}
