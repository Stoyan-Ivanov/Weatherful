package com.stoyan.weatherful.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.stoyan.weatherful.WeatherfulApplication;
import com.stoyan.weatherful.rx.RxBus;
import com.stoyan.weatherful.rx.events.NoInternetAvailableEvent;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Stoyan on 29.1.2018 г..
 */

public class NetworkManager {
    private static NetworkManager instance;
    private WeatherfulAPI weatherfulAPI;
    private QwantAPI qwantAPI;

    public static NetworkManager getInstance(){
        if(instance == null){
            instance = new NetworkManager();
        }
        return instance;
    }

    private NetworkManager(){
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new NetworkConnectionInterceptor() {
                    @Override
                    public void onInternetUnavailable() {
                        RxBus.getInstance().post(new NoInternetAvailableEvent());
                    }
                }).build();

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
