package com.stoyan.weatherful;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.location.LocationRequest;
import com.patloew.rxlocation.RxLocation;
import com.stoyan.weatherful.persistence.models.Location;
import com.stoyan.weatherful.rx.RxUtils;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * Created by stoyan.ivanov2 on 4/17/2018.
 */

@Singleton
public class LocationTracker {
    private Context mContext;
    private RxLocation mRxLocation;

    @Inject
    public LocationTracker() {
        mContext = WeatherfulApplication.getStaticContext();
        mRxLocation = new RxLocation(mContext);
    }

    public Observable<com.stoyan.weatherful.persistence.models.Location> getCurrentLocation() {

        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            return mRxLocation.location().updates(locationRequest)
                    .flatMap(location -> mRxLocation.geocoding().fromLocation(location).toObservable())
                    .map(address -> {
                        String cityName = address.getLocality();
                        String countryName = address.getCountryName();
                        double latitude = address.getLatitude();
                        double longitude = address.getLongitude();

                        return new com.stoyan.weatherful.persistence.models.Location(cityName, countryName, latitude, longitude);
                    })
                    .compose(RxUtils.applySchedulersObservable());

        } else {
            return Observable.just(new Location());
        }
    }
}
