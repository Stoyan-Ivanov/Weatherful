package com.stoyan.weatherful.ui.day_forecast_fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.stoyan.weatherful.Constants;
import com.stoyan.weatherful.R;
import com.stoyan.weatherful.network.WeatherfulApplication;
import com.stoyan.weatherful.network.models.forecast_full_models.Data;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Stoyan on 28.1.2018 г..
 */

public class DayForecastFragmentPresenter {
    private Data data;
    private Context context;

    public DayForecastFragmentPresenter(final Bundle arguments) {
        getExtras(arguments);
        context = WeatherfulApplication.getStaticContext();
    }


    public void getExtras(final Bundle arguments) {
        data = arguments.getParcelable(Constants.EXTRA_DATA);
    }

    public Drawable getImageDrawable() {
        String drawableName = data.getIcon();
        drawableName = drawableName.replaceAll("-", "_").toLowerCase();

        Context context = WeatherfulApplication.getStaticContext();
        int resID = context.getResources().getIdentifier(drawableName, "drawable", context.getPackageName());

        return context.getResources().getDrawable(resID);
    }

    public String getDate() {
        return getDateFromTimestamp();
    }

    public String getMinTemperature() {
        return context.getString(R.string.min_temperature_field, data.getTemperatureLow());
    }

    public String getMaxTemperature() {
        return context.getString(R.string.max_temperature_field, data.getTemperatureHigh());
    }

    public String getWindSpeed() {
        return context.getString(R.string.wind_speed_field, data.getWindSpeed());
    }


    public String getRainChance() {
        float probability = Float.parseFloat(data.getPrecipProbability()) * 100;
        String displayProbability = String.format("%.2f", probability);

        return context.getString(R.string.rain_chance_field, displayProbability);
    }

    private String getDateFromTimestamp() {
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(Long.valueOf(data.getTime()) * 1000);

        return date.get(Calendar.DAY_OF_MONTH) + "."
                + date.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH)
                + "." + date.get(Calendar.YEAR);
    }
}
