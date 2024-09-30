package com.example.findmypet_android_ui.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.findmypet_android_ui.BR;

public class Pet extends BaseObservable {
    private long id;
    private String name;
    private String colour;
    private long age;
    private Boolean isFound;
    private double longitude;
    private double latitude;
    private String imageURL;
    private String lostDate;
    private String type;
    private Owner owner;

    public Pet(long id, String name, String colour, long age, Boolean isFound, double longitude,
               double latitude, String imageURL, String lostDate, String type, Owner owner) {
        this.id = id;
        this.name = name;
        this.colour = colour;
        this.age = age;
        this.isFound = isFound;
        this.longitude = longitude;
        this.latitude = latitude;
        this.imageURL = imageURL;
        this.lostDate = lostDate;
        this.type = type;
        this.owner = owner;
    }

    public Pet() {
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
    public String getColour() {
        return colour;
    }

    @Bindable
    public long getAge() {
        return age;
    }

    @Bindable
    public Boolean getFound() {
        return isFound;
    }

    @Bindable
    public double getLongitude() {
        return longitude;
    }

    @Bindable
    public double getLatitude() {
        return latitude;
    }

    @Bindable
    public String getImageURL() {
        return imageURL;
    }

    @Bindable
    public String getLostDate() {
        return lostDate;
    }

    @Bindable
    public String getType() {
        return type;
    }

    @Bindable
    public Owner getOwner() {
        return owner;
    }

    public void setId(long id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.id);
    }

    public void setColour(String colour) {
        this.colour = colour;
        notifyPropertyChanged(BR.id);
    }

    public void setAge(long age) {
        this.age = age;
        notifyPropertyChanged(BR.id);
    }

    public void setFound(Boolean found) {
        isFound = found;
        notifyPropertyChanged(BR.id);
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
        notifyPropertyChanged(BR.id);
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
        notifyPropertyChanged(BR.id);
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
        notifyPropertyChanged(BR.id);
    }

    public void setLostDate(String lostDate) {
        this.lostDate = lostDate;
        notifyPropertyChanged(BR.id);
    }

    public void setType(String type) {
        this.type = type;
        notifyPropertyChanged(BR.id);
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
        notifyPropertyChanged(BR.id);
    }
}
