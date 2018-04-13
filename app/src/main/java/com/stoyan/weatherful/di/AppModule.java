package com.stoyan.weatherful.di;

import android.app.Application;

import com.stoyan.weatherful.network.NetworkConnectionInterceptor;
import com.stoyan.weatherful.rx.RxBus;
import com.stoyan.weatherful.rx.events.NoInternetAvailableEvent;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Created by stoyan.ivanov2 on 4/12/2018.
 */

@Module
public class AppModule {
    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return application;
    }

    @Provides
    OkHttpClient provideOkHttp(NetworkConnectionInterceptor networkConnectionInterceptor){
        return new OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor).build();
    }

    @Provides
    NetworkConnectionInterceptor provideNetworkInterceptor(RxBus rxBus){
        return new NetworkConnectionInterceptor() {
            @Override
            public void onInternetUnavailable() {
                rxBus.post(new NoInternetAvailableEvent());
            }
        };
    }

    @Provides
    RxBus provideRxBus() {
        return new RxBus();
    }
}
