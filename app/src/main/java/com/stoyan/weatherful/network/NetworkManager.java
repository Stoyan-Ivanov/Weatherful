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
import io.reactivex.android.schedulers.AndroidSchedulers;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Stoyan on 29.1.2018 г..
 */

public class NetworkManager {
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

    public WeatherfulAPI getWeatherfulAPI() {
        return weatherfulAPI;
    }

    public QwantAPI getQwantAPI() {
        return qwantAPI;
    }
}
