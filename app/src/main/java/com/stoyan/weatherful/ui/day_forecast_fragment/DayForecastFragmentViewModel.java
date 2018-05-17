package com.stoyan.weatherful.ui.day_forecast_fragment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import com.stoyan.weatherful.network.models.forecast_full_models.Data;
import com.stoyan.weatherful.viewmodel.BaseViewModel;
import java.util.ArrayList;

/**
 * Created by stoyan.ivanov2 on 5/2/2018.
 */

public class DayForecastFragmentViewModel extends BaseViewModel {
    private MutableLiveData<Data> mData;
    private MutableLiveData<String> mLiveDate;
    private final int PERCENT_MULTIPLIER = 100;

    public DayForecastFragmentViewModel() {
        mLiveDate = new MutableLiveData<>();
        mData = new MutableLiveData<>();
    }

    public void setData(final Data data) {
        mData.setValue(data);
    }

    public LiveData<Integer> getImageIconId() {
        return Transformations.map(mData, data -> data.getIcon().getResourceId());
    }

    public LiveData<String> getDate() {
        mLiveDate.setValue(mData.getValue().getTime());
        return mLiveDate;
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
        return Transformations.map(mData, Data::getForecastSummary);
    }

    public LiveData<Long> getSunriseTime() {
        return Transformations.map(mData, Data::getSunriseTime);
    }

    public LiveData<Long> getSunsetTime() {
        return Transformations.map(mData, Data::getSunsetTime);
    }

    @Override
    protected void inject() {
        getViewModelComponent().inject(this);
    }
}
