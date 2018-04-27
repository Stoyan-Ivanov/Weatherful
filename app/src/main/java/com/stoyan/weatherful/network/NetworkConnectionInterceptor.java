package com.stoyan.weatherful.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.stoyan.weatherful.WeatherfulApplication;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by stoyan.ivanov2 on 4/2/2018.
 */

public abstract class NetworkConnectionInterceptor implements Interceptor {

    public abstract void onInternetUnavailable();

    public NetworkConnectionInterceptor(){
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (!isNetworkAvailable()) {
            onInternetUnavailable();
        }
        return chain.proceed(request);
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) WeatherfulApplication.getStaticContext()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
