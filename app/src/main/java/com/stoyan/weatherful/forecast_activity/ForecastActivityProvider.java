package com.stoyan.weatherful.forecast_activity;


import android.content.Intent;

import com.stoyan.weatherful.Constants;
import com.stoyan.weatherful.models.Location;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class ForecastActivityProvider implements ForecastActivityContract {
    private Location location;

    public ForecastActivityProvider(Intent intent) {
        getExtras(intent);
    }

    @Override
    public void getAdapter() {

    }

    @Override
    public String getHeader() {
        return location.getLocationName() + "," + location.getCountry();
    }

    @Override
    public void startActivity() {

    }

    @Override
    public void getExtras(Intent intent) {
        location = intent.getParcelableExtra(Constants.EXTRA_LOCATION);
    }
}
