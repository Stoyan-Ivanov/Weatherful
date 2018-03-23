package com.stoyan.weatherful.ui.location_activity;

import android.content.Intent;
import android.util.Log;

import com.stoyan.weatherful.R;
import com.stoyan.weatherful.db.Location;
import com.stoyan.weatherful.db.LocationsProvider;
import com.stoyan.weatherful.network.NetworkManager;
import com.stoyan.weatherful.network.WeatherfulApplication;
import com.stoyan.weatherful.network.models.forecast_summary_models.ForecastSummaryResponse;
import com.stoyan.weatherful.network.models.image_response_models.Picture;
import com.stoyan.weatherful.ui.add_location_activity.AddLocationActivity;
import com.stoyan.weatherful.view_utils.recyclerview_utils.locations_recyclerview.LocationViewHolder;
import com.stoyan.weatherful.view_utils.recyclerview_utils.locations_recyclerview.LocationsRecyclerViewAdapter;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class LocationActivityPresenter implements LocationActivityContract {

    private ArrayList<Location> locations;
    private LocationActivity locationActivity;
    private CompositeDisposable disposables = new CompositeDisposable();

    public LocationActivityPresenter(LocationActivity activity) {
        this.locationActivity = activity;
    }

    @Override
    public LocationsRecyclerViewAdapter getAdapter() {
        return null;//LocationsRecyclerViewAdapter(this, LocationsProvider.getInstance().getLocations());
    }

    public void fabOnclick() {
        Intent intent = new Intent(locationActivity, AddLocationActivity.class);
        locationActivity.startActivity(intent);
    }

    public void downloadData() {
        disposables.add(
                Observable.just(locations)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMapIterable(locations1 -> locations1)
                        .flatMap(this::downloadLocationImage)
                        .toList()
                        .map(ArrayList::new)
                        .subscribe(getLocationConsumer(), getErrorConsumer())
        );
    }

    private Consumer<ArrayList<Location>> getLocationConsumer() {
        return locations -> {
            LocationActivityPresenter.this.locations = locations;
            locationActivity.notifyDatasetChanged();
        };
    }

    public Consumer<? super Throwable> getErrorConsumer() {
        return new Consumer<Throwable>() {
            @Override
            public void accept(final Throwable throwable) throws Exception {
                locationActivity.showError(throwable);
            }
        };
    }

    private Observable<Location> downloadLocationImage(Location location) {
        return NetworkManager
                .getInstance()
                .getQwantAPI()
                .getLocationImage(location.toString())
                .map(imageResponse -> imageResponse.getData().getResult().getPictures())
                .flatMapIterable(pictures -> pictures)
                .first(new Picture("www.google.com"))
                .map(Picture::getThumbnailUrl)
                .map(s -> {
                    location.setImageUrl(s);
                    return location;
                })
                .toObservable();
    }

    public void getForecastSummary(final LocationViewHolder viewHolder, final Location location) {
        Observable<ForecastSummaryResponse> observableForecastSummary = NetworkManager
                .getInstance()
                .getWeatherfulAPI()
                .getForecastSummaryResponse(location.getLatitude(),
                        location.getLongitude());

        observableForecastSummary
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getForecastSummaryObserver(viewHolder));
    }

    private DisposableObserver<ForecastSummaryResponse> getForecastSummaryObserver(final LocationViewHolder viewHolder) {
        DisposableObserver<ForecastSummaryResponse> forecastSummaryObserver = new DisposableObserver<ForecastSummaryResponse>() {
            @Override
            public void onNext(ForecastSummaryResponse forecastSummaryResponse) {
                viewHolder.setForecastSummary(forecastSummaryResponse.getHourly().getSummary());

                viewHolder.setTemperature(forecastSummaryResponse.getHourly()
                        .getData().get(0).getTemperature()
                        + WeatherfulApplication.getStringFromId(R.string.degree_symbol));
            }

            @Override
            public void onError(Throwable e) {
                Log.d("SII", "onError: Forecast summary " + e.getMessage());
            }

            @Override
            public void onComplete() {
            }
        };

        disposables.add(forecastSummaryObserver);
        return forecastSummaryObserver;
    }

    @Override
    public void onViewDestroy() {
        Log.d("SII", "onViewDestroy: LocationActivity");
        disposables.clear();
        locationActivity = null;
    }

    public ArrayList<Location> getLocations() {
        locations = LocationsProvider.getInstance().getLocations();
        return locations;
    }


}
