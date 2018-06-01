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
import com.stoyan.weatherful.rx.RxUtils;
import com.stoyan.weatherful.viewmodel.BaseViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class LocationActivityViewModel extends BaseViewModel {
    private MutableLiveData<ArrayList<LocationForecastSummaryWrapper>> mLocationForecastSummaryWrappers;
    private MutableLiveData<LocationForecastSummaryWrapper> mCurrentLocationWrapper;

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

    @SuppressLint("CheckResult")
    public void downloadCurrentLocationData() {
        addDisposable(locationTracker.getCurrentLocation()
                .doOnError(throwable -> Log.d("SII", "downloadCurrentLocationData: " + throwable.getMessage()))
                .subscribe(currentLocation -> {
                    if(currentLocation.getLocationName() == null) {
                        mCurrentLocationWrapper.setValue(null);
                    } else {
                        mCurrentLocationWrapper.setValue(new LocationForecastSummaryWrapper(currentLocation));
                        addDisposable(mDataManager.getCurrentLocationDataObservable(mCurrentLocationWrapper.getValue())
                                .doOnError(throwable -> Log.d(getClass().getName(), "DownloadCurrentLocation: " + throwable.getMessage()))
                                .subscribe(getCurrentLocationConsumer()));
                    }
                }));
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
