package com.stoyan.weatherful.ui.location_activity;

import android.content.Intent;
import android.util.Log;

import com.stoyan.weatherful.Constants;
import com.stoyan.weatherful.R;
import com.stoyan.weatherful.network.NetworkManager;
import com.stoyan.weatherful.network.WeatherfulApplication;
import com.stoyan.weatherful.network.models.forecast_summary_models.ForecastSummaryResponse;
import com.stoyan.weatherful.network.models.image_response_models.ImageResponse;
import com.stoyan.weatherful.network.models.image_response_models.Picture;
import com.stoyan.weatherful.ui.add_location_activity.AddLocationActivity;
import com.stoyan.weatherful.ui.forecast_activity.ForecastActivity;
import com.stoyan.weatherful.db.LocationsProvider;
import com.stoyan.weatherful.db.Location;
import com.stoyan.weatherful.view_utils.recyclerview_utils.locations_recyclerview.LocationViewHolder;
import com.stoyan.weatherful.view_utils.recyclerview_utils.locations_recyclerview.LocationsRecyclerViewAdapter;
import com.stoyan.weatherful.view_utils.recyclerview_utils.locations_recyclerview.OnItemClickListener;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class LocationActivityPresenter implements LocationActivityContract{
    private LocationActivity locationActivity;
    private static CompositeDisposable disposables = new CompositeDisposable();

    public LocationActivityPresenter(LocationActivity activity) {
        this.locationActivity = activity;
    }

    @Override
    public LocationsRecyclerViewAdapter getAdapter() {
        return new LocationsRecyclerViewAdapter(this, LocationsProvider.getInstance().getLocations(),
                new OnItemClickListener() {
            @Override
            public void OnItemClick(Location location) {

                Intent intent = new Intent(locationActivity, ForecastActivity.class);
                intent.putExtra(Constants.EXTRA_LOCATION, location);
                locationActivity.startActivity(intent);
            }

            @Override
            public void OnItemLongClick(Location location) {
                LocationsProvider.getInstance().deleteLocation(location);
            }
        });
    }

    public void fabOnclick() {
        Intent intent = new Intent(locationActivity, AddLocationActivity.class);
        locationActivity.startActivity(intent);
    }

    public void getLocationImageUrl(final LocationViewHolder viewHolder, final Location location) {
        Observable<ImageResponse> observableImageResponse = NetworkManager
                .getInstance()
                .getQwantAPI()
                .getLocationImage(location.toString());

        observableImageResponse
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<ImageResponse, String>() {
                    @Override
                    public String apply(ImageResponse imageResponse) throws Exception {
                        ArrayList<Picture> pictures = imageResponse.getData().getResult().getPictures();
                        return "https:" + pictures.get(0).getThumbnailUrl();
                    }
                })
                .subscribe(getLocationImageUrlObserver(viewHolder));
    }

    private static DisposableObserver<String> getLocationImageUrlObserver(final LocationViewHolder viewHolder) {
        DisposableObserver<String> locationImageUrlObserver = new DisposableObserver<String>() {
            @Override
            public void onNext(String imageUrl) {
                viewHolder.setLocationPicture(imageUrl);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("SII", "onError: location observer" + e.getMessage());
            }

            @Override
            public void onComplete() {}
        };

        disposables.add(locationImageUrlObserver);
        return locationImageUrlObserver;
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

    private DisposableObserver<ForecastSummaryResponse> getForecastSummaryObserver (final LocationViewHolder viewHolder) {
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
            public void onComplete() {}
        };

        disposables.add(forecastSummaryObserver);
        return forecastSummaryObserver;
    }

    @Override
    public void onViewDestroy() {
        Log.d("SII", "onViewDestroy: LocationActivity");
        disposables.dispose();
        locationActivity = null;

    }
}
