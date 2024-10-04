package com.example.findmypet_android_ui.ui.updateposter;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.findmypet_android_ui.R;
import com.example.findmypet_android_ui.databinding.FragmentUpdatePosterBinding;
import com.example.findmypet_android_ui.model.Owner;
import com.example.findmypet_android_ui.model.Pet;
import com.example.findmypet_android_ui.model.Poster;
import com.example.findmypet_android_ui.service.PosterApiService;
import com.example.findmypet_android_ui.service.RetrofitInstance;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePosterFragment extends Fragment implements OnMapReadyCallback {

    private UpdatePosterViewModel viewModel;
    private FragmentUpdatePosterBinding binding;
    private View view;
    private Poster poster;
    private Pet pet;
    private Owner owner;
    private GoogleMap mapFragment;
    private LatLng selectedLocation;
    private UpdatePosterClickHandlers clickHandlers;
    private NavController navController;
    private ActivityResultLauncher<Intent> imagePickerLauncher;

    public static UpdatePosterFragment newInstance() {
        return new UpdatePosterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication()))
                .get(UpdatePosterViewModel.class);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_update_poster, container, false);
        view = binding.getRoot();
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        poster = UpdatePosterFragmentArgs.fromBundle(getArguments()).getPoster();
        pet = poster.getPet();
        owner = pet.getOwner();

        clickHandlers = new UpdatePosterClickHandlers(poster, pet, owner, getContext(),viewModel);

        binding.setPoster(poster);
        binding.setPet(pet);
        binding.setOwner(owner);
        binding.setClickHandler(clickHandlers);

        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                        Uri selectedImageUri = result.getData().getData();
                        if (selectedImageUri != null) {
                            String fileName = getFileName(selectedImageUri);
                            binding.fileNameTextView.setText(fileName != null ? fileName : "File Name Unavailable");
                            binding.uploadImageButton.setText("Image Selected");
                            uploadImage(selectedImageUri);
                        }
                    }
                });

        clickHandlers.setImagePickerLauncher(imagePickerLauncher);

        return view;

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapFragment = googleMap;

        // TODO: nice to have: set default location - users current location IF location permission set up
        // LatLng defaultLocation = new LatLng(-34, 151);
        // mapFragment.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10));
        clickHandlers.mapClick(mapFragment);
    }


    private byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }


    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (nameIndex >= 0) {
                        result = cursor.getString(nameIndex);
                    }
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    private void uploadImage(Uri selectedImageUri) {
        try {
            InputStream inputStream = getContext().getContentResolver().openInputStream(selectedImageUri);
            byte[] bytes = getBytes(inputStream);

            RequestBody requestFile = RequestBody.create(
                    MediaType.parse(getContext().getContentResolver().getType(selectedImageUri)), bytes
            );

            MultipartBody.Part body = MultipartBody.Part.createFormData(
                    "file", getFileName(selectedImageUri), requestFile
            );

            PosterApiService service = RetrofitInstance.getService();
            Call<ResponseBody> call = service.uploadImage(body);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            String imageUrl = response.body().string();
                            pet.setImageURL(imageUrl);

                            Toast.makeText(getContext(), "Image uploaded successfully", Toast.LENGTH_SHORT).show();

                            Glide.with(getContext())
                                    .load(imageUrl)
                                    .placeholder(R.drawable.ic_upload_placeholder)
                                    .error(R.drawable.error_image)
                                    .into(binding.imagePreview);

                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Failed to read response", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        try {
                            String errorBody = response.errorBody().string();
                            Toast.makeText(getContext(), "Image upload failed: " + errorBody, Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Image upload failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getContext(), "Image upload failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Failed to read image data", Toast.LENGTH_SHORT).show();
        }
    }






}