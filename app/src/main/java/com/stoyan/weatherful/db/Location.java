package com.stoyan.weatherful.db;

import android.os.Parcel;
import android.os.Parcelable;

import com.stoyan.weatherful.network.models.forecast_summary_models.ForecastSummaryResponse;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class Location implements Parcelable {
    private String mId;
    private String mLocationName;
    private String mCountry;
    private Coordinates mCoordinates;
    private Image mImage;
    private ForecastSummaryResponse mForecastSummary;

    public Location(String locationName, String country, double latitude, double longitude) {
        this.mId = locationName + country;
        this.mLocationName = locationName;
        this.mCountry = country;
        this.mCoordinates = new Coordinates(latitude, longitude);
    }

    public Location(String locationName, String country, double latitude, double longitude
            , String thumbnailUrl, String fullImageUrl) {

        this(locationName, country, latitude, longitude);
        this.mImage = new Image(thumbnailUrl, fullImageUrl);
    }

    public Location(String locationName, String country, double latitude, double longitude
            , String thumbnailUrl, String fullImageUrl, ForecastSummaryResponse forecastSummary) {

        this(locationName, country, latitude, longitude, thumbnailUrl, fullImageUrl);
        this.mForecastSummary = forecastSummary;
    }


    public String getLocationName() {
        return mLocationName;
    }

    public String getCountry() {
        return mCountry;
    }

    public double getLatitude() {
        return mCoordinates.getLatitude();
    }

    public double getLongitude() {
        return mCoordinates.getLongitude();
    }

    public String getThumbnailUrl() {
        return mImage == null? null: mImage.getThumbnailUrl();
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        if(mImage == null) {
            mImage = new Image();
        }
        mImage.setThumbnailUrl(thumbnailUrl);
    }

    public String getFullImageUrl() {
        return mImage == null? null: mImage.getFullImageUrl();
    }

    public void setFullImageUrl(String fullImageUrl) {
        if(mImage == null) {
            mImage = new Image();
        }
        mImage.setFullImageUrl(fullImageUrl);
    }

    public ForecastSummaryResponse getForecastSummary(){
        return mForecastSummary;
    }

    public void setForecastSummary(ForecastSummaryResponse forecastSummary) {
        this.mForecastSummary = forecastSummary;
    }

    @Override
    public String toString() {
        return  mLocationName + ", " + mCountry;
    }

    protected Location(Parcel in) {
        mLocationName = in.readString();
        mCountry = in.readString();
        mCoordinates = (Coordinates) in.readValue(Coordinates.class.getClassLoader());
        mImage = (Image) in.readValue(Image.class.getClassLoader());;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mLocationName);
        dest.writeString(mCountry);
        dest.writeValue(mCoordinates);
        dest.writeValue(mImage);
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