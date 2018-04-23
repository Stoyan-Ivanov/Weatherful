package com.stoyan.weatherful.di;

import com.stoyan.weatherful.db.room.LocationsDatabase;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by stoyan.ivanov2 on 4/12/2018.
 */
@Singleton
@Component(modules = {AppModule.class, RoomModule.class})
public interface AppComponent {
    PresenterComponent presenterComponent();

    LocationsDatabase locationsDatabase();
}
