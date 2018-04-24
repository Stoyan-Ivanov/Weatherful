package com.stoyan.weatherful.persistence.models;

import com.stoyan.weatherful.network.models.forecast_summary_models.ForecastSummaryResponse;

/**
 * Created by stoyan.ivanov2 on 4/20/2018.
 */

public class LocationForecastSummaryWrapper {
    private Location mLocation;
    private ForecastSummaryResponse mForecastSummaryResponse;

    public LocationForecastSummaryWrapper(Location mLocation) {
        this.mLocation = mLocation;
    }

    public Location getLocation() {
        return mLocation;
    }

    public void setLocation(Location mLocation) {
        this.mLocation = mLocation;
    }

    public ForecastSummaryResponse getForecastSummaryResponse() {
        return mForecastSummaryResponse;
    }

    public void setForecastSummaryResponse(ForecastSummaryResponse mForecastSummaryResponse) {
        this.mForecastSummaryResponse = mForecastSummaryResponse;
    }
}
