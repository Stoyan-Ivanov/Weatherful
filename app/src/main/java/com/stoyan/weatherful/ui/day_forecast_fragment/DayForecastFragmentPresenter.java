package com.stoyan.weatherful.ui.day_forecast_fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.stoyan.weatherful.Constants;
import com.stoyan.weatherful.network.WeatherfulApplication;
import com.stoyan.weatherful.network.models.forecast_full_models.Data;

import java.util.Calendar;

/**
 * Created by Stoyan on 28.1.2018 г..
 */

public class DayForecastFragmentPresenter implements DayForcastFragmentContract {
    private Data data;

    public DayForecastFragmentPresenter(final Bundle arguments) {
        getExtras(arguments);
    }

    @Override
    public void getExtras(final Bundle arguments) {
        data = arguments.getParcelable(Constants.EXTRA_DATA);
    }

    @Override
    public Drawable getImageDrawable() {
        String drawableName = data.getIcon();
        drawableName = drawableName.replaceAll("-", "_").toLowerCase();

        Context context = WeatherfulApplication.getStaticContext();
        int resID = context.getResources().getIdentifier(drawableName, "drawable", context.getPackageName());

        return context.getResources().getDrawable(resID);
    }

    @Override
    public String getDate() {
        return getDateFromTimestamp();
    }

    @Override
    public String getMinTemperature() {
        return "Min Temperature: " + data.getTemperatureLow() + "\u2103";
    }

    @Override
    public String getMaxTemperature() {
        return "Max Temperature: " + data.getTemperatureHigh() + "\u2103";
    }

    @Override
    public String getWindSpeed() {
        return "Wind speed: " + data.getWindSpeed();
    }

    @Override
    public String getRainChance() {
        float probability = Float.parseFloat(data.getPrecipProbability()) * 100;
        return "Chance of raining: " + probability + "%";
    }

    private String getDateFromTimestamp() {
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(Long.valueOf(data.getTime()) * 1000);

        return date.get(Calendar.DAY_OF_MONTH) + "." + date.get(Calendar.MONTH)
                + "." + date.get(Calendar.YEAR);
    }
}
