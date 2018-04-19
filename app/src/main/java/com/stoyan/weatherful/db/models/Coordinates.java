package com.stoyan.weatherful.db.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by stoyan.ivanov on 3/21/2018.
 */

public class Coordinates implements Parcelable {
    private double mLatitude;
    private double mLongitude;

    public Coordinates(double latitude, double longitude) {
        this.mLatitude = latitude;
        this.mLongitude = longitude;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    protected Coordinates(Parcel in) {
        mLatitude = in.readDouble();
        mLongitude = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(mLatitude);
        dest.writeDouble(mLongitude);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Coordinates> CREATOR = new Parcelable.Creator<Coordinates>() {
        @Override
        public Coordinates createFromParcel(Parcel in) {
            return new Coordinates(in);
        }

        @Override
        public Coordinates[] newArray(int size) {
            return new Coordinates[size];
        }
    };
}