package com.stoyan.weatherful.ui.location_activity;

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
       addDisposable(mDataManager.getLocationDataObservable()
               .doOnError(throwable -> Log.d(getClass().getName(), "DownloadData: " + throwable))
               .subscribe(getLocationConsumer()));
    }

    private Consumer<ArrayList<LocationForecastSummaryWrapper>> getLocationConsumer() {
        return wrappers -> mLocationForecastSummaryWrappers.setValue(wrappers);
    }

    public LiveData<ArrayList<LocationForecastSummaryWrapper>> getLocationForecastWrappers() {
        return mLocationForecastSummaryWrappers;
    }

    public void downloadCurrentLocationData() {
        addDisposable(mDataManager.getCurrentLocationDataObservable(mCurrentLocationWrapper.getValue())
                .doOnError(throwable -> Log.d(getClass().getName(), "DownloadCurrentLocation: " + throwable))
                .subscribe(getCurrentLocationConsumer()));
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
