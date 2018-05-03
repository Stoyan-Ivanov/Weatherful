package com.stoyan.weatherful.ui.forecast_activity;

import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.util.Log;

import com.stoyan.weatherful.Constants;
import com.stoyan.weatherful.DataManager;
import com.stoyan.weatherful.network.models.forecast_full_models.Data;
import com.stoyan.weatherful.persistence.models.Location;
import com.stoyan.weatherful.rx.RxBus;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * Created by stoyan.ivanov2 on 5/2/2018.
 */

public class ForecastActivityViewModel {
    private MutableLiveData<Location> mLocation;
    private MutableLiveData<ArrayList<Data>> mWeeklyForecast;

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
        getExtras(intent);
    }

    public String getHeader() {
        return mLocation.toString();
    }

    public void getExtras(Intent intent) {
        mLocation = intent.getParcelableExtra(Constants.EXTRA_LOCATION);
    }

    public String getImageUrl() {
        return mLocation.getValue().getLocationImageFull();
    }

    public void downloadWeeklyForecast() {
        Log.d("SII", "downloadWeeklyForecast: " + mLocation.toString());
        //subscribeToEventBus();
        mDataManager.getWeeklyForecastObservable(mLocation.getValue())
                .subscribe(getWeeklyForecastConsumer(), getErrorConsumer());
    }

    private Consumer<? super Throwable> getErrorConsumer() {
        return (Consumer<Throwable>) throwable -> Log.d("SII", "getErrorConsumer: " + throwable);
    }

    private Consumer<? super ArrayList<Data>> getWeeklyForecastConsumer() {
        return weeklyForecast -> {
            this.mWeeklyForecast.getValue().clear();
            this.mWeeklyForecast.getValue().addAll(weeklyForecast);
            //view.notifyDataSetChanged();
        };
    }

    public ArrayList<Data> getWeeklyForecast() {
        mWeeklyForecast.setValue(new ArrayList<>());
        return mWeeklyForecast.getValue();
    }
}
