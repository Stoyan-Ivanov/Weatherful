package com.stoyan.weatherful.ui.forecast_activity;


import com.stoyan.weatherful.ui.base_ui.BaseActivityContract;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public interface ForecastActivityContract extends BaseActivityContract{
    void notifyDataSetChanged();
    void showError(Throwable throwable);
}
