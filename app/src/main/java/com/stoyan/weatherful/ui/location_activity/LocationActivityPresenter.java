package com.stoyan.weatherful.ui.location_activity;

import android.util.Log;

import com.stoyan.weatherful.db.Location;
import com.stoyan.weatherful.db.LocationsProvider;
import com.stoyan.weatherful.network.NetworkManager;
import com.stoyan.weatherful.network.models.image_response_models.Picture;
import com.stoyan.weatherful.ui.base_ui.BasePresenter;
import com.stoyan.weatherful.ui.base_ui.BasePresenterContract;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class LocationActivityPresenter extends BasePresenter<LocationActivityContract> {
    private ArrayList<Location> locations;
    private CompositeDisposable disposables = new CompositeDisposable();

    public LocationActivityPresenter(LocationActivityContract view) {
        super(view);
    }

    public void fabOnclick() {
        view.startNewActivity();
    }

    public void downloadData() {
        disposables.add(
                Observable.just(LocationsProvider.getInstance().getLocations())
                        .flatMapIterable(locations -> locations)
                        .flatMap(this::downloadLocationImage)
                        .flatMap(this::downloadForecastSummary)
                        .toList()
                        .map(ArrayList::new)
                        .subscribe(getLocationConsumer(), getErrorConsumer())
        );
    }

    private Consumer<ArrayList<Location>> getLocationConsumer() {
        return locations -> {
            LocationActivityPresenter.this.locations.addAll(locations);
            view.notifyDataSetChanged();
        };
    }

    private Consumer<? super Throwable> getErrorConsumer() {
        return (Consumer<Throwable>) throwable -> view.showError(throwable);

    }

    private Observable<Location> downloadLocationImage(Location location) {
        return NetworkManager
                .getInstance()
                .getQwantAPI()
                .getLocationImage(location.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(imageResponse -> imageResponse.getData().getResult().getPictures())
                .flatMapIterable(pictures -> pictures)
                .first(new Picture("www.google.com"))
                .map(Picture::getThumbnailUrl)
                .map(s -> {
                    Log.d("SII", "downloadLocationImage: " + "http:" + s);
                    location.setImageUrl("https:" + s);
                    return location;
                }).toObservable();
    }

    private Observable<Location> downloadForecastSummary(Location location) {
        return NetworkManager
                .getInstance()
                .getWeatherfulAPI()
                .getForecastSummaryResponse(location.getLatitude(), location.getLongitude())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(forecastSummaryResponse -> {
                    location.setForecastSummary(forecastSummaryResponse);
                    return location;
                });
    }

    @Override
    public void onViewDestroy() {
        disposables.clear();
        view = null;
    }

    public ArrayList<Location> getLocations() {
        locations = new ArrayList<>();
        return locations;
    }
}
