package com.stoyan.weatherful.db;

import android.os.Parcel;
import android.os.Parcelable;

import com.stoyan.weatherful.network.models.forecast_summary_models.ForecastSummaryResponse;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class Location implements Parcelable {
    private String locationName;
    private String country;
    private Coordinates coordinates;
    private String imageUrl;
    private ForecastSummaryResponse forecastSummary;

    public Location(String locationName, String country, double latitude, double longitude) {
        this.locationName = locationName;
        this.country = country;
        this. coordinates = new Coordinates(latitude, longitude);
    }

    public Location(String locationName, String country, double latitude, double longitude
            , String imageUrl) {

        this(locationName, country, latitude, longitude);
        this.imageUrl = imageUrl;
    }

    public Location(String locationName, String country, double latitude, double longitude
            , String imageUrl, ForecastSummaryResponse forecastSummary) {

        this(locationName, country, latitude, longitude, imageUrl);
        this.forecastSummary = forecastSummary;
    }


    public String getLocationName() {
        return locationName;
    }

    public String getCountry() {
        return country;
    }

    public double getLatitude() {
        return coordinates.getLatitude();
    }

    public double getLongitude() {
        return coordinates.getLongitude();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ForecastSummaryResponse getForecastSummary(){
        return forecastSummary;
    }

    public void setForecastSummary(ForecastSummaryResponse forecastSummary) {
        this.forecastSummary = forecastSummary;
    }

    @Override
    public String toString() {
        return  locationName + ", " + country;
    }

    protected Location(Parcel in) {
        locationName = in.readString();
        country = in.readString();
        coordinates = (Coordinates) in.readValue(Coordinates.class.getClassLoader());
        imageUrl = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(locationName);
        dest.writeString(country);
        dest.writeValue(coordinates);
        dest.writeString(imageUrl);
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