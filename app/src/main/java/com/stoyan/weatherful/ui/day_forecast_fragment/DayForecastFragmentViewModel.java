package com.stoyan.weatherful.ui.day_forecast_fragment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.os.Bundle;

import com.stoyan.weatherful.Constants;
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

//    public Drawable getImageDrawable() {
//        if(mData.getValue() != null) {
//            return context.getResources().getDrawable(mData.getValue().getIcon().getResourceId());
//        } else {
//            return null;
//        }
//    }

    public LiveData<Integer> getImageIconId() {
        return Transformations.map(mData, data -> data.getIcon().getResourceId());
    }



    public LiveData<String> getDate() {
        MutableLiveData<String> liveDate = new MutableLiveData<>();
        liveDate.setValue(getDateFromTimestamp());
        return liveDate;
    }

//    public String getTemperature() {
//        float temperatureHigh = Float.parseFloat(mData.getValue().getTemperatureHigh());
//        float temperatureLow = Float.parseFloat(mData.getValue().getTemperatureLow());
//        return context.getString(R.string.temperature_field, (int)temperatureHigh, (int)temperatureLow);
//    }

    public LiveData<Integer> getTemperatureLow() {
        return Transformations.map(mData, data -> (int) Float.parseFloat(data.getTemperatureLow()));
    }

    public LiveData<Integer> getTemperatureHigh() {
        return Transformations.map(mData, data -> (int) Float.parseFloat(data.getTemperatureHigh()));
    }

//    public String getWindSpeed() {
//        return context.getString(R.string.wind_speed_field, mData.getValue().getWindSpeed());
//    }

    public LiveData<String> getWindSpeed() {
        return Transformations.map(mData, Data::getWindSpeed);
    }


//    public String getRainChance() {
//        int probability = (int) (Float.parseFloat(mData.getValue().getPrecipProbability()) * PERCENT_MULTIPLIER);
//        return context.getString(R.string.rain_chance_field, probability);
//    }

    public LiveData<Integer> getRainChance() {
        return Transformations.map(mData, data -> (int) (Float.parseFloat(data.getPrecipProbability()) * PERCENT_MULTIPLIER));
    }

//    public String getHumidity() {
//        int humidity = (int) (mData.getValue().getHumidity() * PERCENT_MULTIPLIER);
//        return context.getString(R.string.humidity_field, humidity);
//    }

    public LiveData<Integer> getHumidity() {
        return Transformations.map(mData, data -> (int) data.getHumidity() * PERCENT_MULTIPLIER);
    }

//    public String getForecastSummary() {
//        return mData.getValue().getForecastSummary();
//    }

    public LiveData<String> getForecastSummary() {
        return Transformations.map(mData, Data::getPrecipProbability);
    }

//    public String getSunriseTime() {
//        return getTimeFromUnixTime(mData.getValue().getSunriseTime());
//    }

    public LiveData<String> getSunriseTime() {
        return Transformations.map(mData, data -> getTimeFromUnixTime(data.getSunriseTime()));
    }

//    public String getSunsetTime() {
//        return getTimeFromUnixTime(mData.getValue().getSunsetTime());
//    }

    public LiveData<String> getSunsetTime() {
        return Transformations.map(mData, data -> getTimeFromUnixTime(data.getSunsetTime()));
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
