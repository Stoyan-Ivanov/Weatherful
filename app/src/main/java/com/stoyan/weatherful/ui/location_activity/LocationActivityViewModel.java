package com.stoyan.weatherful.ui.location_activity;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.stoyan.weatherful.DataManager;
import com.stoyan.weatherful.LocationTracker;
import com.stoyan.weatherful.persistence.models.Location;
import com.stoyan.weatherful.persistence.models.LocationForecastSummaryWrapper;
import com.stoyan.weatherful.rx.RxBus;
import com.stoyan.weatherful.viewmodel.BaseViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

@SuppressLint("CheckResult")
public class LocationActivityViewModel extends BaseViewModel {
    private MutableLiveData<ArrayList<LocationForecastSummaryWrapper>> mLocationForecastSummaryWrappers;
    private MutableLiveData<LocationForecastSummaryWrapper> mCurrentLocationWrapper;

    @Inject
    RxBus mRxBus;
    @Inject
    DataManager mDataManager;
    @Inject
    LocationTracker locationTracker;

    @Override
    protected void inject() {
        getViewModelComponent().inject(this);
    }

    public LocationActivityViewModel() {
        mLocationForecastSummaryWrappers = new MutableLiveData<>();
        mCurrentLocationWrapper = new MutableLiveData<>();
        mCurrentLocationWrapper.setValue(
                new LocationForecastSummaryWrapper(locationTracker.getCurrentLocation()));
    }

    public void downloadData() {
        mDataManager.getLocationDataObservable().subscribe(getLocationConsumer(), getErrorConsumer());
    }

    private Consumer<ArrayList<LocationForecastSummaryWrapper>> getLocationConsumer() {
        return wrappers -> mLocationForecastSummaryWrappers.setValue(wrappers);
    }

    private Consumer<? super Throwable> getErrorConsumer() {
        return (Consumer<Throwable>) throwable -> Log.d(getClass().getName(), "getErrorConsumer: " + throwable);
    }

    public LiveData<ArrayList<LocationForecastSummaryWrapper>> getLocationForecastWrappers() {
        return mLocationForecastSummaryWrappers;
    }

    public void downloadCurrentLocationData() {
        mDataManager.getCurrentLocationDataObservable(mCurrentLocationWrapper.getValue())
                .subscribe(getCurrentLocationConsumer(), getErrorConsumer());
    }

    private Consumer<LocationForecastSummaryWrapper> getCurrentLocationConsumer() {
        return wrapper -> mCurrentLocationWrapper.setValue(wrapper);
    }

    public LiveData<LocationForecastSummaryWrapper> getCurrentLocationWrapper() {
        return mCurrentLocationWrapper;
    }

    public void deleteLocation(Location location) {
        mDataManager.deleteLocation(location);
    }
}
