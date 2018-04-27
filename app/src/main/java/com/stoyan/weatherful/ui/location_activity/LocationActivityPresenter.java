package com.stoyan.weatherful.ui.location_activity;

import android.os.Bundle;
import android.util.Log;

import com.stoyan.weatherful.Constants;
import com.stoyan.weatherful.DataManager;
import com.stoyan.weatherful.LocationTracker;
import com.stoyan.weatherful.persistence.models.Location;
import com.stoyan.weatherful.persistence.models.LocationForecastSummaryWrapper;
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
                .compose(RxUtils.applySchedulersObservable())
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

        if(mCurrentLocationWrapper.getLocation() != null) {
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


    public Bundle getCurrentLocationData() {
        Bundle currentLocationData = new Bundle();

        currentLocationData.putString(Constants.CURRENT_LOCATION_NAME,
                mCurrentLocationWrapper.getLocation().getLocationName());
        currentLocationData.putInt(Constants.CURRENT_LOCATION_TEMPERATURE,
                (int) mCurrentLocationWrapper.getForecastSummaryResponse().getHourly().getData().get(0).getTemperature());
        currentLocationData.putString(Constants.CURRENT_LOCATION_FORECAST_SUMMARY,
                mCurrentLocationWrapper.getForecastSummaryResponse().getHourly().getSummary());
        currentLocationData.putString(Constants.CURRENT_LOCATION_IMAGE_URL,
                mCurrentLocationWrapper.getLocation().getLocationImageFull());

        return currentLocationData;
    }

    public void onCurrentLocationClicked() {
        view.startNewForecastActivity(mCurrentLocationWrapper.getLocation());
    }
}
