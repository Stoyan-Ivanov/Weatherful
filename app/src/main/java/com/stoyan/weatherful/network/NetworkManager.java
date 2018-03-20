package com.stoyan.weatherful.network;

import android.provider.ContactsContract;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.stoyan.weatherful.R;
import com.stoyan.weatherful.db.Location;
import com.stoyan.weatherful.network.models.forecast_full_models.ForecastFullResponse;
import com.stoyan.weatherful.network.models.forecast_summary_models.ForecastSummaryResponse;
import com.stoyan.weatherful.network.models.image_response_models.ImageResponse;
import com.stoyan.weatherful.network.models.image_response_models.Picture;
import com.stoyan.weatherful.view_utils.recyclerview_utils.forecast_recyclerview.ForecastRecyclerviewAdapter;
import com.stoyan.weatherful.view_utils.recyclerview_utils.locations_recyclerview.LocationViewHolder;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Stoya on 29.1.2018 г..
 */

public class NetworkManager implements NetworkManagerContract {
    private static NetworkManager instance;
    private final CompositeDisposable disposables = new CompositeDisposable();

    private Retrofit weatherfulRetrofit;
    private WeatherfulAPI weatherfulAPI;

    private Retrofit qwantRetrofit;
    private QwantAPI qwantAPI;

    public static NetworkManager getInstance(){
        if(instance == null){
            instance = new NetworkManager();
        }
        return instance;
    }

    private NetworkManager(){
        weatherfulRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.FORECAST_SERVICE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        weatherfulAPI = weatherfulRetrofit.create(WeatherfulAPI.class);

        qwantRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.IMAGE_SERVICE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        qwantAPI = qwantRetrofit.create(QwantAPI.class);
    }

    @Override
    public void getLocationImageUrl(final LocationViewHolder viewHolder, final Location location) {
        Observable<ImageResponse> observableImageResponse = qwantAPI.getLocationImage(location.toString());

        observableImageResponse
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getLocationImageUrlObserver(viewHolder));
    }

    private DisposableObserver<ImageResponse> getLocationImageUrlObserver(final LocationViewHolder viewHolder) {
        DisposableObserver<ImageResponse> locationImageUrlObserver = new DisposableObserver<ImageResponse>() {
            @Override
            public void onNext(ImageResponse imageResponse) {
                ArrayList <Picture> pictures = imageResponse.getData().getResult().getPictures();
                viewHolder.setLocationPicture("https:" + pictures.get(0).getThumbnailUrl());
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

    @Override
    public void getForecastSummary(final LocationViewHolder viewHolder, final Location location) {
        Observable<ForecastSummaryResponse> observableForecastSummary = weatherfulAPI.getForecastSummaryResponse(location.getLatitude(),
                location.getLongitude());

        observableForecastSummary
                .subscribeOn(Schedulers.newThread())
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
    public void getWeeklyForecast(final Location location, final ForecastRecyclerviewAdapter adapter) {
        Observable<ForecastFullResponse> observableFullForecast = weatherfulAPI.getFullForecastResponse(location.getLatitude(),
                location.getLongitude());

        observableFullForecast
                .observeOn(Schedulers.newThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(getWeeklyForecastObserver(adapter));
    }

    private DisposableObserver<ForecastFullResponse> getWeeklyForecastObserver(final ForecastRecyclerviewAdapter adapter) {
        DisposableObserver<ForecastFullResponse> weeklyForecastObserver = new DisposableObserver<ForecastFullResponse>() {
            @Override
            public void onNext(ForecastFullResponse forecastFullResponse) {
                adapter.setNewData(forecastFullResponse.getDaily().getData());
            }

            @Override
            public void onError(Throwable e) {
                Log.d("SII", "onError: full forecast " + e.getMessage());
            }

            @Override
            public void onComplete() {}
        };

        disposables.add(weeklyForecastObserver);
        return weeklyForecastObserver;
    }

    public void onViewDestroy() {
        if(!disposables.isDisposed()) {
            disposables.dispose();
        }
    }
}
