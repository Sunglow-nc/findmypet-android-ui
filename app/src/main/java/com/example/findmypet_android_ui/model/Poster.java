package com.example.findmypet_android_ui.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.findmypet_android_ui.BR;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Poster extends BaseObservable implements Parcelable {
    private Long id;
    private String datePosted;
    private String description;
    private String title;
    private Pet pet;

    public Poster(Long id, String datePosted, String description, String title, Pet pet) {
        this.id = id;
        this.datePosted = datePosted;
        this.description = description;
        this.title = title;
        this.pet = pet;
    }

    public Poster() {
    }

    protected Poster(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        datePosted = in.readString();
        description = in.readString();
        title = in.readString();
        pet = in.readParcelable(Pet.class.getClassLoader());
    }

    public static final Creator<Poster> CREATOR = new Creator<Poster>() {
        @Override
        public Poster createFromParcel(Parcel in) {
            return new Poster(in);
        }

        @Override
        public Poster[] newArray(int size) {
            return new Poster[size];
        }
    };

    @Bindable
    public Long getId() {
        return id;
    }

    @Bindable
    public String getDatePosted() {
        return datePosted;
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    @Bindable
    public Pet getPet() {
        return pet;
    }

    public void setId(Long id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
        notifyPropertyChanged(BR.id);
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.id);
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.id);
    }

    public void setPet(Pet pet) {
        this.pet = pet;
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
        parcel.writeString(datePosted);
        parcel.writeString(description);
        parcel.writeString(title);
        parcel.writeParcelable(pet, i);
    }
}
