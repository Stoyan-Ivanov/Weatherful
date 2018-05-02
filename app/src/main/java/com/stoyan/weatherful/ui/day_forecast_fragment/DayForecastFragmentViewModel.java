package com.stoyan.weatherful.ui.day_forecast_fragment;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.stoyan.weatherful.Constants;
import com.stoyan.weatherful.R;
import com.stoyan.weatherful.WeatherfulApplication;
import com.stoyan.weatherful.network.models.forecast_full_models.Data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by stoyan.ivanov2 on 5/2/2018.
 */

public class DayForecastFragmentViewModel extends ViewModel {
    private MutableLiveData<Data> mData;
    private Context context = WeatherfulApplication.getStaticContext();
    private final int PERCENT_MULTIPLIER = 100;
    private final int TIME_MULTIPLIER = 1000;

    public void setExtras(final Bundle arguments) {
        mData.setValue(arguments.getParcelable(Constants.EXTRA_DATA));
    }

    public Drawable getImageDrawable() {
        if(mData.getValue() != null) {
            return context.getResources().getDrawable(mData.getValue().getIcon().getResourceId());
        } else {
            return null;
        }
    }

    public String getDate() {
        return getDateFromTimestamp();
    }

    public String getTemperature() {
        float temperatureHigh = Float.parseFloat(mData.getValue().getTemperatureHigh());
        float temperatureLow = Float.parseFloat(mData.getValue().getTemperatureLow());
        return context.getString(R.string.temperature_field, (int)temperatureHigh, (int)temperatureLow);
    }

    public String getWindSpeed() {
        return context.getString(R.string.wind_speed_field, mData.getValue().getWindSpeed());
    }


    public String getRainChance() {
        int probability = (int) (Float.parseFloat(mData.getValue().getPrecipProbability()) * PERCENT_MULTIPLIER);
        return context.getString(R.string.rain_chance_field, probability);
    }

    public String getHumidity() {
        int humidity = (int) (mData.getValue().getHumidity() * PERCENT_MULTIPLIER);
        return context.getString(R.string.humidity_field, humidity);
    }

    public String getForecastSummary() {
        return mData.getValue().getForecastSummary();
    }

    public String getSunriseTime() {
        return getTimeFromUnixTime(mData.getValue().getSunriseTime());
    }

    public String getSunsetTime() {
        return getTimeFromUnixTime(mData.getValue().getSunsetTime());
    }

    private String getDateFromTimestamp() {
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(Long.valueOf(mData.getValue().getTime()) * TIME_MULTIPLIER);

        return date.get(Calendar.DAY_OF_MONTH) + "."
                + date.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH)
                + "." + date.get(Calendar.YEAR);
    }

    private String getTimeFromUnixTime(long unixTime) {
        return new SimpleDateFormat("HH:mm:ss")
                .format(new Date(unixTime * TIME_MULTIPLIER));
    }
}
