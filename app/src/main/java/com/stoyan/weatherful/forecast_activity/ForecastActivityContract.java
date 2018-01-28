package com.stoyan.weatherful.forecast_activity;


import android.content.Intent;

import com.stoyan.weatherful.view_utils.recyclerview_utils.forecast_recyclerview.ForecastRecyclerviewAdapter;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public interface ForecastActivityContract {

    ForecastRecyclerviewAdapter getAdapter();
    String getHeader();
    void getExtras(Intent intent);
}
