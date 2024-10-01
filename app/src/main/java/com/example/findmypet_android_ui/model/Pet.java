package com.example.findmypet_android_ui.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.findmypet_android_ui.BR;

public class Pet extends BaseObservable {
    private Long id;
    private String name;
    private String colour;
    private Long age;
    private Boolean isFound;
    private double longitude;
    private double latitude;
    private String imageURL;
    private String lostDate;
    private String type;
    private Owner owner;

    public Pet(Long id, String name, String colour, Long age, Boolean isFound, double longitude,
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
    public Long getId() {
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
    public String getAge() {
        if(age == null){
            return null;
        }
        return age.toString();
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

    public void setId(Long id) {
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

    public void setAge(String age) {
        this.age = Long.parseLong(age);
        notifyPropertyChanged(BR.id);
    }

    public void setAge(Long age) {
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
