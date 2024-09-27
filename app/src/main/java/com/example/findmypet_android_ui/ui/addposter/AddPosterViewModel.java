package com.example.findmypet_android_ui.ui.addposter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddPosterViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AddPosterViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}