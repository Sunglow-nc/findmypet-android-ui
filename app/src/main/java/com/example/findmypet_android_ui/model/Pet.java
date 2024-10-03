package com.example.findmypet_android_ui.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.findmypet_android_ui.BR;

public class Pet extends BaseObservable implements Parcelable {
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

    protected Pet(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        name = in.readString();
        colour = in.readString();
        if (in.readByte() == 0) {
            age = null;
        } else {
            age = in.readLong();
        }
        byte tmpIsFound = in.readByte();
        isFound = tmpIsFound == 0 ? null : tmpIsFound == 1;
        longitude = in.readDouble();
        latitude = in.readDouble();
        imageURL = in.readString();
        lostDate = in.readString();
        type = in.readString();
        owner = in.readParcelable(Owner.class.getClassLoader());
    }

    public static final Creator<Pet> CREATOR = new Creator<Pet>() {
        @Override
        public Pet createFromParcel(Parcel in) {
            return new Pet(in);
        }

        @Override
        public Pet[] newArray(int size) {
            return new Pet[size];
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
        parcel.writeString(colour);
        if (age == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(age);
        }
        parcel.writeByte((byte) (isFound == null ? 0 : isFound ? 1 : 2));
        parcel.writeDouble(longitude);
        parcel.writeDouble(latitude);
        parcel.writeString(imageURL);
        parcel.writeString(lostDate);
        parcel.writeString(type);
        parcel.writeParcelable(owner, i);
    }
}
