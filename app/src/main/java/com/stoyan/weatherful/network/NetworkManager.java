package com.stoyan.weatherful.network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Stoyan on 29.1.2018 г..
 */
@Singleton
public class NetworkManager {
    private WeatherfulAPI mWeatherfulAPI;
    private QwantAPI mQwantAPI;

    @Inject
    public NetworkManager(OkHttpClient client) {

        Retrofit weatherfulRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.FORECAST_SERVICE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        mWeatherfulAPI = weatherfulRetrofit.create(WeatherfulAPI.class);

        Retrofit qwantRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.IMAGE_SERVICE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        mQwantAPI = qwantRetrofit.create(QwantAPI.class);
    }

    public WeatherfulAPI getWeatherfulAPI() {
        return mWeatherfulAPI;
    }

    public QwantAPI getQwantAPI() {
        return mQwantAPI;
    }
}
