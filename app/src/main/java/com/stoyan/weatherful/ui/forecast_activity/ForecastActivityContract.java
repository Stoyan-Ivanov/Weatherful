package com.stoyan.weatherful.ui.forecast_activity;


/**
 * Created by Stoyan on 27.1.2018 г..
 */

public interface ForecastActivityContract {
    void notifyDataSetChanged();
    void showError(Throwable throwable);
}
