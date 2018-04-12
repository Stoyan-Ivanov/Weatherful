package com.stoyan.weatherful.ui.day_forecast_fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.stoyan.weatherful.Constants;
import com.stoyan.weatherful.R;
import com.stoyan.weatherful.WeatherfulApplication;
import com.stoyan.weatherful.network.models.forecast_full_models.Data;
import com.stoyan.weatherful.ui.base_ui.contract.BaseViewContract;
import com.stoyan.weatherful.ui.base_ui.presenter.BasePresenter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Stoyan on 28.1.2018 г..
 */

public class DayForecastFragmentPresenter extends BasePresenter<BaseViewContract> {
    private Data data;
    private Context context;
    private final int PERCENT_MULTIPLIER = 100;
    private final int TIME_MULTIPLIER = 1000;


    public DayForecastFragmentPresenter(final Bundle arguments, BaseViewContract view) {
        getExtras(arguments);
        context = WeatherfulApplication.getStaticContext();
    }


    public void getExtras(final Bundle arguments) {
        data = arguments.getParcelable(Constants.EXTRA_DATA);
    }

    public Drawable getImageDrawable() {
        return context.getResources().getDrawable(data.getIcon().getResourceId());
    }

    public String getDate() {
        return getDateFromTimestamp();
    }

    public String getTemperature() {
        float temperatureHigh = Float.parseFloat(data.getTemperatureHigh());
        float temperatureLow = Float.parseFloat(data.getTemperatureLow());
        return context.getString(R.string.temperature_field, (int)temperatureHigh, (int)temperatureLow);
    }

    public String getWindSpeed() {
        return context.getString(R.string.wind_speed_field, data.getWindSpeed());
    }


    public String getRainChance() {
       int probability = (int) (Float.parseFloat(data.getPrecipProbability()) * PERCENT_MULTIPLIER);
       return context.getString(R.string.rain_chance_field, probability);
    }

    public String getHumidity() {
        int humidity = (int) (data.getHumidity() * PERCENT_MULTIPLIER);
        return context.getString(R.string.humidity_field, humidity);
    }

    public String getForecastSummary() {
        return data.getForecastSummary();
    }

    public String getSunriseTime() {
        return getTimeFromUnixTime(data.getSunriseTime());
    }

    public String getSunsetTime() {
        return getTimeFromUnixTime(data.getSunsetTime());
    }

    private String getDateFromTimestamp() {
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(Long.valueOf(data.getTime()) * TIME_MULTIPLIER);

        return date.get(Calendar.DAY_OF_MONTH) + "."
                + date.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH)
                + "." + date.get(Calendar.YEAR);
    }

    private String getTimeFromUnixTime(long unixTime) {
        return new SimpleDateFormat("HH:mm:ss")
                .format(new Date(unixTime * TIME_MULTIPLIER));
    }

    @Override
    public void onViewDestroy() {}
}
