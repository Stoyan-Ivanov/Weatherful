package com.stoyan.weatherful.ui.location_activity;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.stoyan.weatherful.R;
import com.stoyan.weatherful.db.Location;
import com.stoyan.weatherful.db.LocationsProvider;
import com.stoyan.weatherful.network.NetworkManager;
import com.stoyan.weatherful.network.WeatherfulApplication;
import com.stoyan.weatherful.network.models.forecast_summary_models.ForecastSummaryResponse;
import com.stoyan.weatherful.network.models.image_response_models.ImageResponse;
import com.stoyan.weatherful.network.models.image_response_models.Picture;
import com.stoyan.weatherful.ui.add_location_activity.AddLocationActivity;
import com.stoyan.weatherful.view_utils.recyclerview_utils.locations_recyclerview.LocationViewHolder;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static io.reactivex.Observable.just;

/**
 * Created by stoyan.ivanov on 3/22/2018.
 */

public class LocationsViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Location>> locations;
    private MutableLiveData<ArrayList<String>> imageUrls;
    private static CompositeDisposable disposables = new CompositeDisposable();

    public LocationsViewModel() {
        locations = new MutableLiveData<>();
        imageUrls = new MutableLiveData<>();

        fetchLocations();
        fetchImageUrls();
        Log.d("SII", "LocationsViewModel: " + imageUrls.getValue());
    }

    private void fetchLocations() {
        locations.setValue(LocationsProvider.getInstance().getLocations());
    }

    private void fetchImageUrls() {
        if(locations.getValue() != null) {
            Log.d("SII", "fetchimageurls ");
            for (Location location : locations.getValue()) {
                Log.d("SII", "LocationsViewModel: "+ location.toString());
                getLocationImageUrl(location);
            }
        }
    }

    public ArrayList<Location> getLocations() {
        return locations.getValue();
    }

    public MutableLiveData<ArrayList<String>> getImageUrls() {
        return imageUrls;
    }

    public void fabOnclick() {
        Context context = WeatherfulApplication.getStaticContext();
        Intent intent = new Intent(context, AddLocationActivity.class);
        context.startActivity(intent);
    }

    private void getLocationImageUrl(final Location location) {
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
                .subscribe(getLocationImageUrlObserver());
    }

    private DisposableObserver<String> getLocationImageUrlObserver() {
        final DisposableObserver<String> locationImageUrlObserver = new DisposableObserver<String>() {
            @Override
            public void onNext(String url) {
                Log.d("SII", "onNext: " + url);
                ArrayList<String> urls = imageUrls.getValue();
                urls.add(url);

                imageUrls.setValue(urls);
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

    public void onViewDestroy() {
        disposables.clear();
    }
}
