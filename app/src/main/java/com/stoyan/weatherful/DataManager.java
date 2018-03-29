package com.stoyan.weatherful;

import android.util.Log;

import com.stoyan.weatherful.db.Location;
import com.stoyan.weatherful.db.LocationsProvider;
import com.stoyan.weatherful.network.NetworkManager;
import com.stoyan.weatherful.network.models.forecast_full_models.Data;
import com.stoyan.weatherful.network.models.forecast_full_models.ForecastFullResponse;
import com.stoyan.weatherful.network.models.image_response_models.Picture;

import java.util.ArrayList;
import java.util.Arrays;

import io.reactivex.Observable;

/**
 * Created by stoyan.ivanov2 on 3/28/2018.
 */

public class DataManager {
    private static DataManager instance;
    private static final String URL_PREFIX = "https:";

    public static DataManager getInstance() {
        if(instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public Observable<ArrayList<Location>> getLocationDataObservable() {
        return Observable.just(LocationsProvider.getInstance().getLocations())
                .flatMapIterable(locations -> locations)
                .flatMap(this::downloadLocationImage)
                .flatMap(this::downloadForecastSummary)
                .toList()
                .map(ArrayList::new)
                .toObservable();
    }

    private Observable<Location> downloadLocationImage(Location location) {
        if(location.getImageUrl() == null) {
           return NetworkManager
                    .getInstance()
                    .getQwantAPI()
                    .getLocationImage(location.toString())
                    .compose(RxUtils.applySchedulers())
                    .map(imageResponse -> imageResponse.getData().getResult().getPictures())
                    .flatMapIterable(pictures -> pictures)
                    .first(new Picture(""))
                    .map(Picture::getThumbnailUrl)
                    .map(s -> {
                        location.setImageUrl(URL_PREFIX + s);
                        LocationsProvider.getInstance().updateLocation(location);
                        return location;
                    }).toObservable();
        }
        return Observable.just(location);
    }

    private Observable<Location> downloadForecastSummary(Location location) {
        return NetworkManager
                .getInstance()
                .getWeatherfulAPI()
                .getForecastSummaryResponse(location.getLatitude(), location.getLongitude())
                .compose(RxUtils.applySchedulers())
                .map(forecastSummaryResponse -> {
                    location.setForecastSummary(forecastSummaryResponse);
                    return location;
                });
    }

    public Observable<ArrayList<Data>> getWeeklyForecastObservable(Location location) {
        return NetworkManager
                .getInstance()
                .getWeatherfulAPI()
                .getFullForecastResponse(location.getLatitude(), location.getLongitude())
                .compose(RxUtils.applySchedulers())
                .map(forecastFullResponse -> forecastFullResponse.getDaily().getData());
    }
}
