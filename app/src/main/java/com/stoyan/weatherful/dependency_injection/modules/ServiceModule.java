package com.stoyan.weatherful.dependency_injection.modules;

import android.appwidget.AppWidgetProvider;

import com.stoyan.weatherful.services.LocationService;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {
    private LocationService mLocationService;

    public ServiceModule(LocationService locationService) {
        this.mLocationService = locationService;
    }

    @Provides
    public LocationService provideWidgetProvider() {
        return mLocationService;
    }

}
