package com.stoyan.weatherful.ui.forecast_activity;


import android.content.Intent;

import com.stoyan.weatherful.Constants;
import com.stoyan.weatherful.network.DataManager;
import com.stoyan.weatherful.db.Location;
import com.stoyan.weatherful.network.models.forecast_full_models.Data;
import com.stoyan.weatherful.rx.RxBus;
import com.stoyan.weatherful.rx.RxUtils;
import com.stoyan.weatherful.rx.events.NoInternetAvailableEvent;
import com.stoyan.weatherful.ui.base_ui.presenter.BasePresenter;

import java.util.ArrayList;

import io.reactivex.functions.Consumer;

import static io.reactivex.Observable.just;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class ForecastActivityPresenter extends BasePresenter<ForecastActivityContract>{
    private Location location;
    private ArrayList<Data> weeklyForecast;

    public ForecastActivityPresenter(Intent intent, ForecastActivityContract view) {
        super(view);
        getExtras(intent);
    }

    private void subscribeToEventBus() {
        addDisposable(RxBus.getInstance().toObservable()
                .compose(RxUtils.applySchedulers())
                .subscribe(event -> {
                    if(event instanceof NoInternetAvailableEvent) {
                        view.showNoInternetView();
                    }
                }));
    }

    public String getHeader() {
        return location.toString();
    }

    public void getExtras(Intent intent) {
        location = intent.getParcelableExtra(Constants.EXTRA_LOCATION);
    }

    public String getImageUrl() {
        return location.getImageUrl();
    }

    public void downloadWeeklyForecast() {
        subscribeToEventBus();
        DataManager.getInstance().getWeeklyForecastObservable(location)
                .subscribe(getWeeklyForecastConsumer(), getErrorConsumer());
    }

    private Consumer<? super Throwable> getErrorConsumer() {
        return (Consumer<Throwable>) throwable -> view.showError(throwable);
    }

    private Consumer<? super ArrayList<Data>> getWeeklyForecastConsumer() {
        return weeklyForecast -> {
            this.weeklyForecast.clear();
            this.weeklyForecast.addAll(weeklyForecast);
            view.notifyDataSetChanged();
        };
    }

    public Location getLocation() {
        return location;
    }

    public ArrayList<Data> getWeeklyForecast() {
        weeklyForecast = new ArrayList<>();
        return weeklyForecast;
    }
}
