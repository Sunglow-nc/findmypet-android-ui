package com.example.findmypet_android_ui.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.findmypet_android_ui.BR;

public class Owner extends BaseObservable {
    private long id;
    private String name;
    private String contactNumber;
    private String emailAddress;

    public Owner(long id, String name, String contactNumber, String emailAddress) {
        this.id = id;
        this.name = name;
        this.contactNumber = contactNumber;
        this.emailAddress = emailAddress;
    }

    public Owner() {
    }

    @Bindable
    public long getId() {
        return id;
    }

    @Bindable
    public String getName() {
        return name;
    }

    @Bindable
    public String getContactNumber() {
        return contactNumber;
    }

    @Bindable
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setId(long id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.id);
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
        notifyPropertyChanged(BR.id);
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        notifyPropertyChanged(BR.id);
    }
}
