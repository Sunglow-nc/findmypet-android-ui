package com.example.findmypet_android_ui.ui.aboutus;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AboutUsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AboutUsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Welcome to PetFinder, your go-to platform for reuniting lost pets with " +
                "their families. We understand the deep emotional bond between pet owners and their " +
                "furry companions, and we know how distressing it can be when a pet goes missing. " +
                "That’s why we’ve created an easy-to-use, online poster system that helps you share " +
                "vital information about your lost pet with your local community and beyond.\n" +
                        "\nOur mission is simple: to connect people and pets, quickly and effectively. " +
                        "Through PetFinder, you can create detailed, location-based posters for your lost " +
                        "pets, including photos, descriptions, and last known locations. We provide " +
                        "a platform where other animal lovers and good Samaritans can view, share, " +
                        "and respond to your posts, increasing the chances of bringing your beloved " +
                        "pet home.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}