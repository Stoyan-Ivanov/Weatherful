package com.stoyan.weatherful.network.models.forecast_full_models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Stoyan on 27.1.2018 г..
 */
public class Data implements Parcelable {
    @SerializedName("temperatureLow")
    private String temperatureLow;
    @SerializedName("time")
    private String time;
    @SerializedName("temperatureHigh")
    private String temperatureHigh;
    @SerializedName("icon")
    private String icon;
    @SerializedName("temperatureMin")
    private String temperatureMin;
    @SerializedName("windSpeed")
    private String windSpeed;
    @SerializedName("humidity")
    private String humidity;
    @SerializedName("precipProbability")
    private String precipProbability;


    public String getTemperatureLow ()
    {
        return temperatureLow;
    }

    public String getTime ()
    {
        return time;
    }

    public void setTime (String time)
    {
        this.time = time;
    }

    public String getTemperatureHigh ()
    {
        return temperatureHigh;
    }

    public String getIcon ()
    {
        return icon;
    }

    public void setIcon (String icon)
    {
        this.icon = icon;
    }

    public String getWindSpeed ()
    {
        return windSpeed;
    }

    public String getPrecipProbability ()
    {
        return precipProbability;
    }


    protected Data(Parcel in) {
        temperatureLow = in.readString();
        time = in.readString();
        temperatureHigh = in.readString();
        icon = in.readString();
        temperatureMin = in.readString();
        windSpeed = in.readString();
        humidity = in.readString();
        precipProbability = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(temperatureLow);
        dest.writeString(time);
        dest.writeString(temperatureHigh);
        dest.writeString(icon);
        dest.writeString(temperatureMin);
        dest.writeString(windSpeed);
        dest.writeString(humidity);
        dest.writeString(precipProbability);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Data> CREATOR = new Parcelable.Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };
}