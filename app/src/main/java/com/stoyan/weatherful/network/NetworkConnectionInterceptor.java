package com.stoyan.weatherful.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by stoyan.ivanov2 on 4/2/2018.
 */

public abstract class NetworkConnectionInterceptor implements Interceptor {

    public abstract void onInternetUnavailable();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if(!NetworkManager.getInstance().isNetworkAvailable()) {
            onInternetUnavailable();
        }
        return chain.proceed(request);
    }
}
