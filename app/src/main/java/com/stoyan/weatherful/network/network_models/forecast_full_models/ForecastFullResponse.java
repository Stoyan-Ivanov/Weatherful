package com.stoyan.weatherful.network.network_models.forecast_full_models;

import java.util.ArrayList;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class ForecastFullResponse {
    private String timezone;

    private String longitude;

    private String latitude;

    private Daily daily;

    public Daily getDaily() {
        return daily;
    }

    public void setDaily(Daily daily) {
        this.daily = daily;
    }

    public String getTimezone ()
    {
        return timezone;
    }

    public void setTimezone (String timezone)
    {
        this.timezone = timezone;
    }

    public String getLongitude ()
    {
        return longitude;
    }

    public void setLongitude (String longitude)
    {
        this.longitude = longitude;
    }

    public String getLatitude ()
    {
        return latitude;
    }

    public void setLatitude (String latitude)
    {
        this.latitude = latitude;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [timezone = "+timezone+", longitude = "+longitude+", latitude = "+latitude+"]";
    }
}
