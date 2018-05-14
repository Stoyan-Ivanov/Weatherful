package com.stoyan.weatherful.ui.forecast_activity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.content.Intent;
import android.util.Log;

import com.stoyan.weatherful.Constants;
import com.stoyan.weatherful.DataManager;
import com.stoyan.weatherful.livedata_utils.SingleLiveEvent;
import com.stoyan.weatherful.network.models.forecast_full_models.Data;
import com.stoyan.weatherful.persistence.models.Location;
import com.stoyan.weatherful.rx.RxBus;
import com.stoyan.weatherful.viewmodel.BaseViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * Created by stoyan.ivanov2 on 5/2/2018.
 */

public class ForecastActivityViewModel  extends BaseViewModel {
    private MutableLiveData<Location> mLocation;
    private MutableLiveData<ArrayList<Data>> mWeeklyForecast;
    private SingleLiveEvent mDataDownloadedEvent;

    @Inject
    protected RxBus mRxBus;
    @Inject
    protected DataManager mDataManager;

    public ForecastActivityViewModel() {
        mLocation = new MutableLiveData<>();
        mWeeklyForecast = new MutableLiveData<>();
        mDataDownloadedEvent = new SingleLiveEvent();
    }

    public void setExtras(Intent intent) {
        mLocation.setValue(intent.getParcelableExtra(Constants.EXTRA_LOCATION));
    }

    public LiveData<String> getHeader() {
        return Transformations.map(mLocation, Location::toString);
    }

    public LiveData<String> getImageUrl() {
        return Transformations.map(mLocation, Location::getLocationImageFull);
    }

    public LiveData<Location> getLocation() {
        return mLocation;
    }

    public void downloadWeeklyForecast(Location location) {
        if(mDataManager != null) {
            mDataManager.getWeeklyForecastObservable(location)
                    .subscribe(getWeeklyForecastConsumer(), getErrorConsumer());
        }

    }

    public LiveData<ArrayList<Data>> getWeeklyForecast() {
        if(mWeeklyForecast != null) {
            return mWeeklyForecast;
        }
        return null;
    }

    private Consumer<? super Throwable> getErrorConsumer() {
        return (Consumer<Throwable>) throwable -> Log.d("SII", "getErrorConsumer: " + throwable);
    }

    private Consumer<? super ArrayList<Data>> getWeeklyForecastConsumer() {
        return weeklyForecast -> {
            mWeeklyForecast.setValue(weeklyForecast);
            onDataDownloaded();
        };
    }

    private void onDataDownloaded() {
        mDataDownloadedEvent.call();
    }

    public SingleLiveEvent getDataDownloadedEvent() {
        return mDataDownloadedEvent;
    }

    @Override
    protected void inject() {
        getViewModelComponent().inject(this);
    }
}
