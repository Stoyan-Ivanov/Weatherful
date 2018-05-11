package com.stoyan.weatherful.dependency_injection.components;

import com.stoyan.weatherful.dependency_injection.modules.AppModule;
import com.stoyan.weatherful.dependency_injection.modules.RoomModule;
import com.stoyan.weatherful.dependency_injection.modules.ViewModelModule;
import com.stoyan.weatherful.persistence.room.LocationsDatabase;
import com.stoyan.weatherful.ui.add_location_activity.AddLocationViewModel;
import com.stoyan.weatherful.ui.day_forecast_fragment.DayForecastFragmentViewModel;
import com.stoyan.weatherful.ui.forecast_activity.ForecastActivityViewModel;
import com.stoyan.weatherful.ui.forecast_pager_activity.ForecastPagerActivityViewModel;
import com.stoyan.weatherful.ui.location_activity.LocationActivityViewModel;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Subcomponent;

/**
 * Created by stoyan.ivanov2 on 4/12/2018.
 */
@Singleton
@Component(modules = {AppModule.class, RoomModule.class})
public interface AppComponent {

    ViewModelComponent viewModelComponent();

    LocationsDatabase locationsDatabase();


    @Subcomponent(modules = {ViewModelModule.class})
    interface ViewModelComponent {

        void inject(AddLocationViewModel addLocationViewModel);

        void inject(DayForecastFragmentViewModel dayForecastFragmentViewModel);

        void inject(ForecastActivityViewModel forecastActivityViewModel);

        void inject(ForecastPagerActivityViewModel forecastPagerActivityViewModel);

        void inject(LocationActivityViewModel locationActivityViewModel);
    }
}
