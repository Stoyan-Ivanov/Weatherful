package com.stoyan.weatherful;

import com.stoyan.weatherful.persistence.LocationsProvider;
import com.stoyan.weatherful.persistence.models.Location;
import com.stoyan.weatherful.persistence.models.LocationForecastSummaryWrapper;
import com.stoyan.weatherful.network.NetworkManager;
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

    public Observable<ArrayList<LocationForecastSummaryWrapper>> getLocationDataObservable() {
        return Observable.just(mLocationProvider.getLocations())
                .flatMapIterable(locations -> locations)
                .map(location -> new LocationForecastSummaryWrapper(location))
                .flatMap(this::downloadLocationImage)
                .flatMap(this::downloadForecastSummary)
                .map(wrapper -> {updateLocation(wrapper.getLocation());
                            return wrapper;})
                .toList()
                .map(ArrayList::new)
                .toObservable();
    }

    private Observable<LocationForecastSummaryWrapper> downloadLocationImage(LocationForecastSummaryWrapper wrapper) {
        if(wrapper.getLocation().getLocationImageThumbnail() == null) {
           return mNetworkManager
                    .getQwantAPI()
                    .getLocationImage(wrapper.getLocation().toString())
                    .compose(RxUtils.applySchedulersObservable())
                    .map(imageResponse -> imageResponse.getData().getResult().getPictures())
                    .flatMapIterable(pictures -> pictures)
                    .firstElement()
                    .map(picture -> {
                        wrapper.getLocation().setLocationImageThumbnail(URL_PREFIX + picture.getThumbnailUrl());
                        wrapper.getLocation().setLocationImageFull(URL_PREFIX + picture.getFullSizeImageUrl());
                        return wrapper;
                    }).toObservable();
        }
        return Observable.just(wrapper);
    }

    public Observable<LocationForecastSummaryWrapper> getCurrentLocationDataObservable(LocationForecastSummaryWrapper wrapper) {
        return Observable.just(wrapper)
                .flatMap(this::downloadLocationImage)
                .flatMap(this::downloadForecastSummary);
    }

    private Observable<LocationForecastSummaryWrapper> downloadForecastSummary(LocationForecastSummaryWrapper wrapper) {
        return mNetworkManager
                .getWeatherfulAPI()
                .getForecastSummaryResponse(wrapper.getLocation().getLatitude(), wrapper.getLocation().getLongitude())
                .compose(RxUtils.applySchedulersObservable())
                .map(forecastSummaryResponse -> {
                    wrapper.setForecastSummaryResponse(forecastSummaryResponse);
                    return wrapper;
                });
    }

    public Observable<ArrayList<Data>> getWeeklyForecastObservable(Location location) {
        return mNetworkManager
                .getWeatherfulAPI()
                .getFullForecastResponse(location.getLatitude(), location.getLongitude())
                .compose(RxUtils.applySchedulersObservable())
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
