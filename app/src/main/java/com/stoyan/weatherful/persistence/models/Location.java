package com.stoyan.weatherful.persistence.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Objects;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

@Entity(tableName = "locations", primaryKeys = {"locationName", "locationCountry"})
public class Location implements Parcelable {
    @NonNull
    @ColumnInfo(name = "locationName")
    private String mLocationName;

    @NonNull
    @ColumnInfo(name = "locationCountry")
    private String mCountry;

    @ColumnInfo(name = "locationLatitude")
    private double mLatitude;

    @ColumnInfo(name = "locationLongitude")
    private double mLongitude;

    @ColumnInfo(name = "locationThumbnail")
    private String mLocationImageThumbnail;

    @ColumnInfo(name = "locationFullImage")
    private String mLocationImageFull;

    @Ignore
    public Location() {}


    public Location(String mLocationName, String mCountry, double mLatitude, double mLongitude) {
        this.mLocationName = mLocationName;
        this.mCountry = mCountry;
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
    }

    @Ignore
    public Location(String mLocationName, String mCountry, double latitude, double longitude
            , String mLocationImageThumbnail, String mLocationImageFull) {

        this(mLocationName, mCountry, latitude, longitude);
        this.mLocationImageThumbnail = mLocationImageThumbnail;
        this.mLocationImageFull = mLocationImageFull;
    }

    public String getLocationName() {
        return mLocationName;
    }

    public void setLocationName(String mLocationName) {
        this.mLocationName = mLocationName;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String mCountry) {
        this.mCountry = mCountry;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
    }

    public String getLocationImageThumbnail() {
        return mLocationImageThumbnail;
    }

    public void setLocationImageThumbnail(String mLocationImageThumbnail) {
        this.mLocationImageThumbnail = mLocationImageThumbnail;
    }

    public String getLocationImageFull() {
        return mLocationImageFull;
    }

    public void setLocationImageFull(String mLocationImageFull) {
        this.mLocationImageFull = mLocationImageFull;
    }

    @Override
    public String toString() {
        return  mLocationName + ", " + mCountry;
    }

    protected Location(Parcel in) {
        mLocationName = in.readString();
        mCountry = in.readString();
        mLatitude = in.readDouble();
        mLongitude = in.readDouble();
        mLocationImageThumbnail = in.readString();
        mLocationImageFull = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mLocationName);
        dest.writeString(mCountry);
        dest.writeDouble(mLatitude);
        dest.writeDouble(mLongitude);
        dest.writeString(mLocationImageThumbnail);
        dest.writeString(mLocationImageFull);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;
        Location location = (Location) o;
        return Double.compare(location.getLatitude(), getLatitude()) == 0 &&
                Double.compare(location.getLongitude(), getLongitude()) == 0 &&
                Objects.equals(getLocationName(), location.getLocationName()) &&
                Objects.equals(getCountry(), location.getCountry()) &&
                Objects.equals(getLocationImageThumbnail(), location.getLocationImageThumbnail()) &&
                Objects.equals(getLocationImageFull(), location.getLocationImageFull());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getLocationName(), getCountry(), getLatitude(), getLongitude(), getLocationImageThumbnail(), getLocationImageFull());
    }
}