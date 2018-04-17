package com.stoyan.weatherful.network;

import com.stoyan.weatherful.db.Location;
import com.stoyan.weatherful.db.LocationsProvider;
import com.stoyan.weatherful.network.models.forecast_full_models.Data;
import com.stoyan.weatherful.rx.RxUtils;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by stoyan.ivanov2 on 3/28/2018.
 */
@Singleton
public class DataManager {
    private static final String URL_PREFIX = "https:";
    private final NetworkManager mNetworkManager;
    private final LocationsProvider mLocationProvider;

    @Inject
    public DataManager(NetworkManager networkManager, LocationsProvider locationProvider) {
        this.mNetworkManager = networkManager;
        this.mLocationProvider = locationProvider;
    }

    public Observable<ArrayList<Location>> getLocationDataObservable() {
        return Observable.just(mLocationProvider.getLocations())
                .flatMapIterable(locations -> locations)
                .flatMap(this::downloadLocationImage)
                .flatMap(this::downloadForecastSummary)
                .toList()
                .map(ArrayList::new)
                .toObservable();
    }

    private Observable<Location> downloadLocationImage(Location location) {
        if(location.getThumbnailUrl() == null) {
           return mNetworkManager
                    .getQwantAPI()
                    .getLocationImage(location.toString())
                    .compose(RxUtils.applySchedulers())
                    .map(imageResponse -> imageResponse.getData().getResult().getPictures())
                    .flatMapIterable(pictures -> pictures)
                    .firstElement()
                    .map(picture -> {
                        location.setThumbnailUrl(URL_PREFIX + picture.getThumbnailUrl());
                        location.setFullImageUrl(URL_PREFIX + picture.getFullSizeImageUrl());
                        //updateLocation(location);
                        return location;
                    }).toObservable();
        }
        return Observable.just(location);
    }

    public Observable<Location> getCurrentLocationDataObservable(Location location) {
        return Observable.just(location)
                .flatMap(this::downloadLocationImage)
                .flatMap(this::downloadForecastSummary);
    }

    private Observable<Location> downloadForecastSummary(Location location) {
        return mNetworkManager
                .getWeatherfulAPI()
                .getForecastSummaryResponse(location.getLatitude(), location.getLongitude())
                .compose(RxUtils.applySchedulers())
                .map(forecastSummaryResponse -> {
                    location.setForecastSummary(forecastSummaryResponse);
                    return location;
                });
    }

    public Observable<ArrayList<Data>> getWeeklyForecastObservable(Location location) {
        return mNetworkManager
                .getWeatherfulAPI()
                .getFullForecastResponse(location.getLatitude(), location.getLongitude())
                .compose(RxUtils.applySchedulers())
                .map(forecastFullResponse -> forecastFullResponse.getDaily().getData())
                .map(data -> { data.remove(0);
                    return data;
                });
    }

    public boolean saveLocation(Location location) {
        return mLocationProvider.saveLocation(location);
    }

    public void deleteLocation(Location location) {
        mLocationProvider.deleteLocation(location);
    }

    public void updateLocation(Location location) {
        mLocationProvider.updateLocation(location);
    }
}
