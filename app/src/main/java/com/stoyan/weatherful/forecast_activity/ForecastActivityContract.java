package com.stoyan.weatherful.forecast_activity;


import android.content.Intent;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public interface ForecastActivityContract {

    void getAdapter();
    String getHeader();
    void startActivity();
    void getExtras(Intent intent);
}
