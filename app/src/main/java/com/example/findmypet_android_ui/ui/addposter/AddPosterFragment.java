package com.example.findmypet_android_ui.ui.addposter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.findmypet_android_ui.R;
import com.example.findmypet_android_ui.databinding.FragmentAddPosterBinding;
import com.example.findmypet_android_ui.model.Owner;
import com.example.findmypet_android_ui.model.Pet;
import com.example.findmypet_android_ui.model.Poster;
import com.example.findmypet_android_ui.service.PosterApiService;
import com.example.findmypet_android_ui.service.RetrofitInstance;
import com.example.findmypet_android_ui.ui.home.HomeFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
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

public class AddPosterFragment extends Fragment implements OnMapReadyCallback {

    private FragmentAddPosterBinding binding;
    private GoogleMap mapFragment;
    private View view;
    private LatLng selectedLocation;
    private AddPosterClickHandler clickHandler;
    private Poster poster;
    private AddPosterViewModel viewModel;
    private Pet pet;
    private Owner owner;
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    private ActivityResultLauncher<String> requestPermissionLauncher;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set up location access before view is created

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted) {
                // Permission is granted
                accessUserLocation();
            } else {
                // Permission is denied
                Toast.makeText(getContext(), "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(AddPosterViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_poster, container, false);
        view = binding.getRoot();
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        poster = new Poster();
        pet = new Pet();
        owner = new Owner();
        clickHandler = new AddPosterClickHandler(poster, pet, owner, getContext(), viewModel);

        binding.setPoster(poster);
        binding.setPet(pet);
        binding.setOwner(owner);
        binding.setClickHandler(clickHandler);

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission();
        } else {
            accessUserLocation();
        }

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

        clickHandler.setImagePickerLauncher(imagePickerLauncher);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapFragment = googleMap;
        LatLng defaultLocation = new LatLng(54.607868, -2.021308);
        mapFragment.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 6));

        selectedLocation = clickHandler.mapClick(mapFragment);

        if (selectedLocation != null) {
            mapFragment.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedLocation, 10));
        }
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

    public void requestLocationPermission(){
        requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION);
    }

    public void accessUserLocation(){
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        selectedLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        if (mapFragment != null) {
                            mapFragment.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedLocation, 10));
                        }
                    }
                });
    }

}