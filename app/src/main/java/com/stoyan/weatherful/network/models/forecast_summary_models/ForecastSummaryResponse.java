package com.stoyan.weatherful.network.models.forecast_summary_models;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class ForecastSummaryResponse {
    @SerializedName("hourly")
    private Hourly hourly;

    public Hourly getHourly() {
        return hourly;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ForecastSummaryResponse)) return false;
        ForecastSummaryResponse that = (ForecastSummaryResponse) o;
        return Objects.equals(getHourly(), that.getHourly());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getHourly());
    }
}
