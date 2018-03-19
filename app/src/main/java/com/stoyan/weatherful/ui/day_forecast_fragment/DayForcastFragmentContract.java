package com.stoyan.weatherful.ui.day_forecast_fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.stoyan.weatherful.ui.BasePresenterContract;

/**
 * Created by Stoyan on 28.1.2018 г..
 */

public interface DayForcastFragmentContract extends BasePresenterContract {

    void getExtras(Bundle bundle);

    Drawable getImageDrawable();

    String getDate();

    String getMinTemperature();

    String getMaxTemperature();

    String getWindSpeed();

    String getRainChance();
}
