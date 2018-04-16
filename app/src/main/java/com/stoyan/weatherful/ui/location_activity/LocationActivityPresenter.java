package com.stoyan.weatherful.ui.location_activity;

import com.stoyan.weatherful.db.Location;
import com.stoyan.weatherful.network.DataManager;
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

public class LocationActivityPresenter extends BasePresenter<LocationActivityContract> {
    private ArrayList<Location> mLocations;
    private Location mMainLocation;

    @Inject RxBus mRxBus;
    @Inject DataManager mDataManager;

    @Override
    protected void inject() {
        getPresenterComponent().inject(this);
    }

    private void subscribeToEventBus() {
        addDisposable(mRxBus.toObservable()
                .compose(RxUtils.applySchedulers())
                .subscribe(event -> {
                    if (event instanceof NoInternetAvailableEvent) {
                        view.showNoInternetView();
                    }
                }));
    }

    public void fabOnClick() {
        view.startNewActivity();
    }

    public void downloadData() {
        subscribeToEventBus();
        addDisposable(mDataManager.getLocationDataObservable()
                .subscribe(getLocationConsumer(), getErrorConsumer())
        );
    }

    private Consumer<ArrayList<Location>> getLocationConsumer() {
        return locations -> {
            LocationActivityPresenter.this.mLocations.clear();
            LocationActivityPresenter.this.mLocations.addAll(locations);
            view.loadMainLocation();
            view.notifyDataSetChanged();
        };
    }

    private Consumer<? super Throwable> getErrorConsumer() {
        return (Consumer<Throwable>) throwable -> view.showError(throwable);
    }

    public ArrayList<Location> getLocations() {
        mLocations = new ArrayList<>();
        return mLocations;
    }

    public void deleteLocation(Location location) {
        mDataManager.deleteLocation(location);
    }

    public String getMainLocationName() {
        return mLocations.get(0).getLocationName();
    }

    public int getMainLocationTemperature() {
        return (int) mLocations.get(0).getForecastSummary().getHourly().getData().get(0).getTemperature();
    }

    public String getMainLocationForecastSummary() {
        return mLocations.get(0).getForecastSummary().getHourly().getSummary();
    }

    public String getMainLocationImageUrl() {
        return mLocations.get(0).getThumbnailUrl();
    }
}
