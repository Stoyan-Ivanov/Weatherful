package com.stoyan.weatherful.ui.day_forecast_fragment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.os.Bundle;

import com.stoyan.weatherful.Constants;
import com.stoyan.weatherful.network.models.forecast_full_models.Data;
import com.stoyan.weatherful.viewmodel.BaseViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by stoyan.ivanov2 on 5/2/2018.
 */

public class DayForecastFragmentViewModel extends BaseViewModel {
    private MutableLiveData<Data> mData;
    private final int PERCENT_MULTIPLIER = 100;
    private final int TIME_MULTIPLIER = 1000;

    public void setExtras(final Bundle arguments) {
        mData = new MutableLiveData<>();
        mData.setValue(arguments.getParcelable(Constants.EXTRA_DATA));
    }

    public LiveData<Integer> getImageIconId() {
        return Transformations.map(mData, data -> data.getIcon().getResourceId());
    }

    public LiveData<String> getDate() {
        MutableLiveData<String> liveDate = new MutableLiveData<>();
        liveDate.setValue(getDateFromTimestamp());
        return liveDate;
    }

    public LiveData<ArrayList<Integer>> getTemperature() {
        return Transformations.map(mData, data -> {
            ArrayList<Integer> temperatures = new ArrayList<>();
            temperatures.add((int) Float.parseFloat(data.getTemperatureHigh()));
            temperatures.add((int) Float.parseFloat(data.getTemperatureLow()));
            return temperatures;
        });
    }

    public LiveData<String> getWindSpeed() {
        return Transformations.map(mData, Data::getWindSpeed);
    }

    public LiveData<Integer> getRainChance() {
        return Transformations.map(mData, data -> (int) (Float.parseFloat(data.getPrecipProbability()) * PERCENT_MULTIPLIER));
    }

    public LiveData<Integer> getHumidity() {
        return Transformations.map(mData, data -> (int) (data.getHumidity() * PERCENT_MULTIPLIER));
    }

    public LiveData<String> getForecastSummary() {
        return Transformations.map(mData, Data::getPrecipProbability);
    }

    public LiveData<String> getSunriseTime() {
        return Transformations.map(mData, data -> getTimeFromUnixTime(data.getSunriseTime()));
    }

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

    @Override
    protected void inject() {
        getViewModelComponent().inject(this);
    }
}
