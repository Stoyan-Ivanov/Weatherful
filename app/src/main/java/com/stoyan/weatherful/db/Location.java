package com.stoyan.weatherful.db;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class Location implements Parcelable {
    private String locationName;
    private String country;
    private double latitude;
    private double longitude;

    public Location(String locationName, String country, double latitude, double longitude) {
        this.locationName = locationName;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getCountry() {
        return country;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    protected Location(Parcel in) {
        locationName = in.readString();
        country = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    @Override
    public String toString() {
        return locationName + "," + country;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(locationName);
        dest.writeString(country);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
}
