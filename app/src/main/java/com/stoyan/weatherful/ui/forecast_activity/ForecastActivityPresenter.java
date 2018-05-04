package com.stoyan.weatherful.ui.forecast_activity;


import android.content.Intent;
import android.util.Log;

import com.stoyan.weatherful.Constants;
import com.stoyan.weatherful.persistence.models.Location;
import com.stoyan.weatherful.DataManager;
import com.stoyan.weatherful.network.models.forecast_full_models.Data;
import com.stoyan.weatherful.rx.RxBus;
import com.stoyan.weatherful.rx.RxUtils;
import com.stoyan.weatherful.rx.events.NoInternetAvailableEvent;
import com.stoyan.weatherful.ui.base_ui.presenter.BasePresenter;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class ForecastActivityPresenter extends BasePresenter<ForecastActivityContract>{
    private Location mLocation;
    private ArrayList<Data> mWeeklyForecast;

    @Inject RxBus mRxBus;
    @Inject DataManager mDataManager;

    @Override
    protected void inject() {
        getPresenterComponent().inject(this);
    }

    private void subscribeToEventBus() {
        addDisposable(mRxBus.toObservable()
                .compose(RxUtils.applySchedulersObservable())
                .subscribe(event -> {
                    if(event instanceof NoInternetAvailableEvent) {
                        view.showNoInternetView();
                    }
                }));
    }

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
        return mLocation.getLocationImageFull();
    }

    public void downloadWeeklyForecast() {
        Log.d("SII", "downloadWeeklyForecast: " + mLocation.toString());
        subscribeToEventBus();
        mDataManager.getWeeklyForecastObservable(mLocation)
                .subscribe(getWeeklyForecastConsumer(), getErrorConsumer());
    }

    private Consumer<? super Throwable> getErrorConsumer() {
        return (Consumer<Throwable>) throwable -> view.showError(throwable);
    }

    private Consumer<? super ArrayList<Data>> getWeeklyForecastConsumer() {
        return weeklyForecast -> {
            this.mWeeklyForecast.clear();
            this.mWeeklyForecast.addAll(weeklyForecast);
            view.notifyDataSetChanged();
        };
    }

    public Location getLocation() {
        return mLocation;
    }

    public ArrayList<Data> getWeeklyForecast() {
        mWeeklyForecast = new ArrayList<>();
        return mWeeklyForecast;
    }
}
