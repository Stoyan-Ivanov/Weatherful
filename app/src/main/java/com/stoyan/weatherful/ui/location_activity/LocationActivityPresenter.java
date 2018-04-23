package com.stoyan.weatherful.ui.location_activity;

import com.stoyan.weatherful.LocationTracker;
import com.stoyan.weatherful.db.models.Location;
import com.stoyan.weatherful.db.models.LocationForecastSummaryWrapper;
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
    private ArrayList<LocationForecastSummaryWrapper> mLocationForecastSummaryWrappers;
    private LocationForecastSummaryWrapper mCurrentLocationWrapper;

    @Inject RxBus mRxBus;
    @Inject DataManager mDataManager;
    @Inject LocationTracker locationTracker;

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

    private Consumer<ArrayList<LocationForecastSummaryWrapper>> getLocationConsumer() {
        return wrappers -> {
            mLocationForecastSummaryWrappers.clear();
            mLocationForecastSummaryWrappers.addAll(wrappers);

            view.notifyDataSetChanged();
        };
    }

    private Consumer<? super Throwable> getErrorConsumer() {
        return (Consumer<Throwable>) throwable -> view.showError(throwable);
    }

    public ArrayList<LocationForecastSummaryWrapper> getLocationForecastWrappers() {
        mLocationForecastSummaryWrappers = new ArrayList<>();
        return mLocationForecastSummaryWrappers;
    }

    public void getCurrentLocation() {
        mCurrentLocationWrapper  = new LocationForecastSummaryWrapper(locationTracker.getCurrentLocation());

        if(mCurrentLocationWrapper != null) {
            addDisposable(mDataManager.getCurrentLocationDataObservable(mCurrentLocationWrapper)
                    .subscribe(getCurrentLocationConsumer(), getErrorConsumer())
            );
        }
    }

    private Consumer<LocationForecastSummaryWrapper> getCurrentLocationConsumer() {
        return wrapper -> {
            mCurrentLocationWrapper = wrapper;
            view.loadCurrentLocation();
        };
    }

    public void deleteLocation(Location location) {
        mDataManager.deleteLocation(location);
    }

    public String getCurrentLocationName() {
        return mCurrentLocationWrapper.getLocation().getLocationName();
    }

    public int getCurrentLocationTemperature() {
        return (int) mCurrentLocationWrapper.getForecastSummaryResponse().getHourly().getData().get(0).getTemperature();
    }

    public String getMainLocationForecastSummary() {
        return mCurrentLocationWrapper.getForecastSummaryResponse().getHourly().getSummary();
    }

    public String getMainLocationImageUrl() {
        return mCurrentLocationWrapper.getLocation().getLocationImageThumbnail();
    }

    public void onCurrentLocationClicked() {
        view.startNewForecastActivity(mCurrentLocationWrapper.getLocation());
    }
}
