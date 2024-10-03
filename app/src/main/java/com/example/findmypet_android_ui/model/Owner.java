package com.example.findmypet_android_ui.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.findmypet_android_ui.BR;

public class Owner extends BaseObservable implements Parcelable {
    private Long id;
    private String name;
    private String contactNumber;
    private String emailAddress;

    public Owner(Long id, String name, String contactNumber, String emailAddress) {
        this.id = id;
        this.name = name;
        this.contactNumber = contactNumber;
        this.emailAddress = emailAddress;
    }

    public Owner() {
    }

    protected Owner(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        name = in.readString();
        contactNumber = in.readString();
        emailAddress = in.readString();
    }

    public static final Creator<Owner> CREATOR = new Creator<Owner>() {
        @Override
        public Owner createFromParcel(Parcel in) {
            return new Owner(in);
        }

        @Override
        public Owner[] newArray(int size) {
            return new Owner[size];
        }
    };

    @Bindable
    public Long getId() {
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

    public void setId(Long id) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(id);
        }
        parcel.writeString(name);
        parcel.writeString(contactNumber);
        parcel.writeString(emailAddress);
    }
}
