package com.stoyan.weatherful.forecast_pager_activity;

import android.content.Intent;

import com.stoyan.weatherful.view_utils.CustomPagerAdapter;

/**
 * Created by Stoyan on 28.1.2018 г..
 */

public interface ForecastPagerActivityContract {

    String getHeader();

    void getExtras(Intent intent);

    CustomPagerAdapter getPagerAdapter();

    int getDefaultPosition();

    int getOffScreenLimit();
}
