package com.stoyan.weatherful.services;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.stoyan.weatherful.DataManager;
import com.stoyan.weatherful.LocationTracker;
import com.stoyan.weatherful.R;
import com.stoyan.weatherful.persistence.models.LocationForecastSummaryWrapper;
import com.stoyan.weatherful.utils.Constants;

import javax.inject.Inject;

import io.reactivex.Observable;

public class LocationService extends Service {
    @Inject DataManager mDataManager;
    LocationTracker mLocationTracker;

    @Override
    public void onCreate() {
        super.onCreate();
        mLocationTracker =  new LocationTracker();
    }

    @SuppressLint("CheckResult")
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        mLocationTracker.getCurrentLocation().subscribe(location ->
            Observable.just(location).flatMap(location1 -> mDataManager
                    .getCurrentLocationDataObservable(new LocationForecastSummaryWrapper(location1)))
                    .subscribe(this::publishResults)
        );
        return null;
    }

    private void publishResults(LocationForecastSummaryWrapper locationForecastSummaryWrapper) {
        if(locationForecastSummaryWrapper != null && locationForecastSummaryWrapper.getLocation() != null) {
            Intent intent = new Intent();
            intent.putExtra(Constants.EXTRA_LOCATION, locationForecastSummaryWrapper.getLocation());
            intent.putExtra("extraForecastTemp", locationForecastSummaryWrapper.getForecastSummaryResponse().getHourly().getData().get(0).getTemperature());
            intent.putExtra("extraForecastSummary", locationForecastSummaryWrapper.getForecastSummaryResponse().getHourly().getSummary());
            sendBroadcast(intent);
        }
    }
}
