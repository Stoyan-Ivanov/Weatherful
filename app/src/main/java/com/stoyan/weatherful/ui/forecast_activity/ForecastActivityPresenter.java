package com.stoyan.weatherful.ui.forecast_activity;


import android.content.Intent;

import com.stoyan.weatherful.Constants;
import com.stoyan.weatherful.network.NetworkManager;
import com.stoyan.weatherful.db.Location;
import com.stoyan.weatherful.network.models.forecast_full_models.Data;
import com.stoyan.weatherful.ui.base_ui.BasePresenter;
import com.stoyan.weatherful.ui.base_ui.BasePresenterContract;

import java.util.ArrayList;
import java.util.Arrays;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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

    public String getHeader() {
        return location.getLocationName() + ", " + location.getCountry();
    }

    public void getExtras(Intent intent) {
        location = intent.getParcelableExtra(Constants.EXTRA_LOCATION);
    }

    public void downloadWeeklyForecast() {
        Observable.just(NetworkManager
                .getInstance()
                .getWeatherfulAPI()
                .getFullForecastResponse(location.getLatitude(), location.getLongitude())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(forecastFullResponse -> forecastFullResponse.getDaily().getData())
                .map(Arrays::asList)
                .map(ArrayList::new)
                .subscribe(getWeeklyForecastConsumer(), getErrorConsumer()));
    }

    private Consumer<? super Throwable> getErrorConsumer() {
        return (Consumer<Throwable>) throwable -> view.showError(throwable);
    }

    private Consumer<? super ArrayList<Data>> getWeeklyForecastConsumer() {
        return weeklyForecast -> {
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
