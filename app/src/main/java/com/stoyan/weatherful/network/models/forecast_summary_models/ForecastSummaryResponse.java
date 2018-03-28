package com.stoyan.weatherful.network.models.forecast_summary_models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class ForecastSummaryResponse {
    @SerializedName("hourly")
    private Hourly hourly;

    public Hourly getHourly() {
        return hourly;
    }

}
