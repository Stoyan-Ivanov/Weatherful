package com.stoyan.weatherful.ui.forecast_activity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.content.Intent;
import android.util.Log;

import com.stoyan.weatherful.Constants;
import com.stoyan.weatherful.DataManager;
import com.stoyan.weatherful.SingleLiveEvent;
import com.stoyan.weatherful.network.models.forecast_full_models.Data;
import com.stoyan.weatherful.persistence.models.Location;
import com.stoyan.weatherful.rx.RxBus;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * Created by stoyan.ivanov2 on 5/2/2018.
 */

public class ForecastActivityViewModel  extends ViewModel {
    private MutableLiveData<Location> mLocation = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Data>> mWeeklyForecast = new MutableLiveData<>();
    private SingleLiveEvent mDataDownloadedEvent = new SingleLiveEvent();

    @Inject
    RxBus mRxBus;
    @Inject
    DataManager mDataManager;

//    private void subscribeToEventBus() {
//        addDisposable(mRxBus.toObservable()
//                .compose(RxUtils.applySchedulersObservable())
//                .subscribe(event -> {
//                    if(event instanceof NoInternetAvailableEvent) {
//                        view.showNoInternetView();
//                    }
//                }));
//    }

    public void setExtras(Intent intent) {
        mLocation.setValue(intent.getParcelableExtra(Constants.EXTRA_LOCATION));
    }

    public LiveData<String> getHeader() {
        return Transformations.map(mLocation, location -> location.toString());
    }

    public LiveData<String> getImageUrl() {
        return Transformations.map(mLocation, location -> location.getLocationImageFull());
    }

    public LiveData<Location> getLocation() {
        return mLocation;
    }

    public void downloadWeeklyForecast() {
        //subscribeToEventBus();
        mDataManager.getWeeklyForecastObservable(mLocation.getValue())
                .subscribe(getWeeklyForecastConsumer(), getErrorConsumer());
    }

    private Consumer<? super Throwable> getErrorConsumer() {
        return (Consumer<Throwable>) throwable -> Log.d("SII", "getErrorConsumer: " + throwable);
    }

    private Consumer<? super ArrayList<Data>> getWeeklyForecastConsumer() {
        return weeklyForecast -> {
            mWeeklyForecast.setValue(new ArrayList<>());
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

    public ArrayList<Data> getWeeklyForecast() {
        //mWeeklyForecast.setValue(new ArrayList<>());
        return mWeeklyForecast.getValue();
    }
}
