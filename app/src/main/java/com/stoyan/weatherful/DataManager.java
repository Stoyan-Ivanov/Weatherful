package com.stoyan.weatherful;

import com.stoyan.weatherful.network.NetworkManager;
import com.stoyan.weatherful.network.models.forecast_full_models.Data;
import com.stoyan.weatherful.persistence.LocationsProvider;
import com.stoyan.weatherful.persistence.models.Location;
import com.stoyan.weatherful.persistence.models.LocationForecastSummaryWrapper;
import com.stoyan.weatherful.rx.RxUtils;

import java.util.ArrayList;
import java.util.Random;

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
    private final int MAX_PICTURE_OFFSET = 10;

    @Inject
    public DataManager(NetworkManager networkManager, LocationsProvider locationProvider) {
        this.mNetworkManager = networkManager;
        this.mLocationProvider = locationProvider;
    }

    public Observable<ArrayList<LocationForecastSummaryWrapper>> getLocationDataObservable() {
        return  mLocationProvider.getLocations()
                .flatMap(locations -> Observable.just(locations)
                    .flatMapIterable(allLocations -> allLocations)
                    .map(LocationForecastSummaryWrapper::new)
                    .flatMap(DataManager.this::downloadLocationImage)
                    .flatMap(DataManager.this::downloadForecastSummary)
                    .map(wrapper -> {
                        updateLocation(wrapper.getLocation());
                        return wrapper;})
                    .toList()
                    .map(ArrayList::new)
                    .toObservable());
    }

    private Observable<LocationForecastSummaryWrapper> downloadLocationImage(LocationForecastSummaryWrapper wrapper) {
        if(wrapper.getLocation().getLocationImageThumbnail() == null) {
            int pictureResultOffset = new Random().nextInt(MAX_PICTURE_OFFSET) + 1;
            return mNetworkManager
                    .getQwantAPI()
                    .getLocationImage(pictureResultOffset, wrapper.getLocation().toString())
                    .toObservable()
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
                .toObservable()
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
                .toObservable()
                .compose(RxUtils.applySchedulersObservable())
                .map(forecastFullResponse -> forecastFullResponse.getDaily().getData())
                .map(data -> { data.remove(0);
                    return data;
                });
    }

    public void saveLocation(Location location) {
        mLocationProvider.saveLocation(location);
    }

    public void deleteLocation(Location location) {
        mLocationProvider.deleteLocation(location);
    }

    public void updateLocation(Location location) {
        mLocationProvider.updateLocation(location);
    }
}
