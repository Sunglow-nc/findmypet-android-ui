package com.example.findmypet_android_ui.ui.aboutus;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AboutUsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<String> headerText;
    private final MutableLiveData<String> developerHeader;
    private final MutableLiveData<String> developerDetail;
    private final MutableLiveData<String> developerNames;

    public AboutUsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Welcome to FindMyPet, your go-to platform for reuniting lost pets with " +
                "their families. We understand the deep emotional bond between pet owners and their " +
                "furry companions, and we know how distressing it can be when a pet goes missing. " +
                "That’s why we’ve created an easy-to-use, online poster system that helps you share " +
                "vital information about your lost pet with your local community and beyond.\n" +
                        "\nOur mission is simple: to connect people and pets, quickly and effectively. " +
                        "Through FindMyPet, you can create detailed, location-based posters for your lost " +
                        "pets, including photos, descriptions, and last known locations. We provide " +
                        "a platform where other animal lovers and good Samaritans can view, share, " +
                        "and respond to your posts, increasing the chances of bringing your beloved " +
                        "pet home.");

        headerText = new MutableLiveData<>();
        headerText.setValue("About FindMyPet");

        developerHeader = new MutableLiveData<>();
        developerHeader.setValue("Who are we");

        developerDetail = new MutableLiveData<>();
        developerDetail.setValue("Brought together for our passion for developing apps which can help " +
                "make a difference.\n");

        developerNames = new MutableLiveData<>();
        developerNames.setValue("Qiujuan Wang: \nhttps://github.com/QWang00\n" +
                "\nDmytro Shakhray: \nhttps://github.com/dimadeloseros1\n" +
                "\nAndrei Vasiliu: \nhttps://github.com/andrei-vasiliu-coding\n" +
                "\nAbi Petheram: \nhttps://github.com/AbiPetheram");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<String> getHeaderText(){
        return headerText;
    }

    public LiveData<String> getDeveloperHeader(){
        return developerHeader;
    }

    public LiveData<String> getDeveloperDetail(){
        return developerDetail;
    }

    public LiveData<String> getDeveloperNames(){
        return developerNames;
    }
}