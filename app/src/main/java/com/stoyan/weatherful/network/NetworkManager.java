package com.stoyan.weatherful.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.stoyan.weatherful.WeatherfulApplication;

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
    private WeatherfulAPI weatherfulAPI;
    private QwantAPI qwantAPI;

    @Inject
    public NetworkManager(OkHttpClient client) {

        Retrofit weatherfulRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.FORECAST_SERVICE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        weatherfulAPI = weatherfulRetrofit.create(WeatherfulAPI.class);

        Retrofit qwantRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.IMAGE_SERVICE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        qwantAPI = qwantRetrofit.create(QwantAPI.class);
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) WeatherfulApplication.getStaticContext()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public WeatherfulAPI getWeatherfulAPI() {
        return weatherfulAPI;
    }

    public QwantAPI getQwantAPI() {
        return qwantAPI;
    }
}
