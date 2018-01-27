package com.stoyan.weatherful.forecast_activity;


import android.content.Intent;

import com.stoyan.weatherful.Constants;
import com.stoyan.weatherful.models.Location;
import com.stoyan.weatherful.network.WeatherfulAPIImpl;
import com.stoyan.weatherful.view_utils.recyclerview_utils.forecast_recyclerview.ForecastRecyclerviewAdapter;
import com.stoyan.weatherful.view_utils.recyclerview_utils.forecast_recyclerview.OnItemClickListener;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class ForecastActivityPresenter implements ForecastActivityContract {
    private Location location;

    public ForecastActivityPresenter(Intent intent) {
        getExtras(intent);
    }

    @Override
    public ForecastRecyclerviewAdapter getAdapter() {
            return new ForecastRecyclerviewAdapter(location, new OnItemClickListener() {
                @Override
                public void OnItemClick() {

                }
            });
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
