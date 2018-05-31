package com.stoyan.weatherful.network.models.forecast_full_models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.stoyan.weatherful.R;

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
    private Icon icon;
    @SerializedName("temperatureMin")
    private String temperatureMin;
    @SerializedName("windSpeed")
    private String windSpeed;
    @SerializedName("humidity")
    private float humidity;
    @SerializedName("summary")
    private String forecastSummary;
    @SerializedName("sunriseTime")
    private long sunriseTime;
    @SerializedName("sunsetTime")
    private long sunsetTime;
    @SerializedName("precipProbability")
    private String precipProbability;

    private String timezone;

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getPrecipProbability() {
        return precipProbability;
    }

    public String getTemperatureLow () {
        return temperatureLow;
    }

    public String getTime () {
        return time;
    }

    public void setTime (String time) {
        this.time = time;
    }

    public String getTemperatureHigh () {
        return temperatureHigh;
    }

    public Icon getIcon () {
        return icon;
    }

    public void setIcon (Icon icon) {
        this.icon = icon;
    }

    public String getWindSpeed () {
        return windSpeed;
    }

    public float getHumidity() {
        return humidity;
    }

    public String getForecastSummary() {
        return forecastSummary;
    }

    public long getSunriseTime() {
        return sunriseTime;
    }

    public long getSunsetTime() {
        return sunsetTime;
    }

    protected Data(Parcel in) {
        temperatureLow = in.readString();
        time = in.readString();
        temperatureHigh = in.readString();
        icon = (Icon) in.readValue(Icon.class.getClassLoader());
        temperatureMin = in.readString();
        windSpeed = in.readString();
        humidity = in.readFloat();
        forecastSummary = in.readString();
        sunriseTime = in.readLong();
        sunsetTime = in.readLong();
        precipProbability = in.readString();
        timezone = in.readString();
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
        dest.writeValue(icon);
        dest.writeString(temperatureMin);
        dest.writeString(windSpeed);
        dest.writeFloat(humidity);
        dest.writeString(forecastSummary);
        dest.writeLong(sunriseTime);
        dest.writeLong(sunsetTime);
        dest.writeString(precipProbability);
        dest.writeString(timezone);
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

    public enum Icon {
        @SerializedName("clear-day")
        CLEAR_DAY(R.drawable.clear_day),
        @SerializedName("clear-night")
        CLEAR_NIGHT(R.drawable.clear_night),
        @SerializedName("cloudy")
        CLOUDY(R.drawable.cloudy),
        @SerializedName("fog")
        FOG(R.drawable.fog),
        @SerializedName("hail")
        HAIL(R.drawable.hail),
        @SerializedName("partly-cloudy-day")
        PARTLY_CLOUDY_DAY(R.drawable.partly_cloudy_day),
        @SerializedName("partly-cloudy-night")
        PARTLY_CLOUDY_NIGHT(R.drawable.partly_cloudy_night),
        @SerializedName("rain")
        RAIN(R.drawable.rain),
        @SerializedName("snow")
        SNOW(R.drawable.snow),
        @SerializedName("thunderstorm")
        THNDERSTORM(R.drawable.thunderstorm),
        @SerializedName("tornado")
        TORNADO(R.drawable.tornado),
        @SerializedName("wind")
        WIND(R.drawable.wind);

        private int resourceId;

        Icon(int resourceId) {
            this.resourceId = resourceId;
        }

        public int getResourceId() {
            return resourceId;
        }
    }
}