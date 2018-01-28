package com.stoyan.weatherful.forecast_activity;


import android.content.Intent;
import com.stoyan.weatherful.Constants;
import com.stoyan.weatherful.forecast_pager_activity.ForecastPagerActivity;
import com.stoyan.weatherful.models.Location;
import com.stoyan.weatherful.network.network_models.forecast_full_models.Data;
import com.stoyan.weatherful.view_utils.recyclerview_utils.forecast_recyclerview.ForecastRecyclerviewAdapter;
import com.stoyan.weatherful.view_utils.recyclerview_utils.forecast_recyclerview.OnItemClickListener;

import java.util.ArrayList;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class ForecastActivityPresenter implements ForecastActivityContract {
    private Location location;
    private ForecastActivity forecastActivity;

    public ForecastActivityPresenter(Intent intent, ForecastActivity activity) {
        getExtras(intent);
        forecastActivity = activity;
    }

    @Override
    public ForecastRecyclerviewAdapter getAdapter() {
            return new ForecastRecyclerviewAdapter(location, new OnItemClickListener() {
                @Override
                public void OnItemClick(final ArrayList<Data> data, final int position) {
                    startNewActivity(data, position);
                }
            });
    }

    @Override
    public String getHeader() {
        return location.getLocationName() + "," + location.getCountry();
    }


    @Override
    public void getExtras(Intent intent) {
        location = intent.getParcelableExtra(Constants.EXTRA_LOCATION);
    }

    private void startNewActivity(final ArrayList<Data> data, final int position) {
        Intent intent = new Intent(forecastActivity, ForecastPagerActivity.class);
        intent.putExtra(Constants.EXTRA_LOCATION, location);
        intent.putExtra(Constants.EXTRA_DATA, data);
        intent.putExtra(Constants.EXTRA_POSITION, position);

        forecastActivity.startActivity(intent);
    }
}
