package com.stoyan.weatherful.network.models.forecast_summary_models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class Data {
    @SerializedName("summary")
    private String summary;
    @SerializedName("temperature")
    private String temperature;

    public String getSummary() {
        return summary;
    }

    public String getTemperature() {
        return temperature;
    }
}
