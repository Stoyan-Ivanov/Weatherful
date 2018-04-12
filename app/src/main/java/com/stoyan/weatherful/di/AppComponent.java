package com.stoyan.weatherful.di;

import com.stoyan.weatherful.WeatherfulApplication;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by stoyan.ivanov2 on 4/12/2018.
 */

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(WeatherfulApplication application);

//    @Component.Builder
//    interface Builder {
//        AppComponent build();
//        @BindsInstance
//        Builder application(Application application);
//    }
}
