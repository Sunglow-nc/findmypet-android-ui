package com.example.findmypet_android_ui.model;

import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.findmypet_android_ui.BR;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Poster extends BaseObservable {
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
}
