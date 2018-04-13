package com.stoyan.weatherful.di;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by stoyan.ivanov2 on 4/12/2018.
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    PresenterComponent presenterComponent();
}
