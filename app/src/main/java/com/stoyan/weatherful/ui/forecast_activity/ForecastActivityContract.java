package com.stoyan.weatherful.ui.forecast_activity;


import android.content.Intent;

import com.stoyan.weatherful.ui.BasePresenterContract;
import com.stoyan.weatherful.view_utils.recyclerview_utils.forecast_recyclerview.ForecastRecyclerviewAdapter;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public interface ForecastActivityContract extends BasePresenterContract {

    String getHeader();
    void getExtras(Intent intent);
}
