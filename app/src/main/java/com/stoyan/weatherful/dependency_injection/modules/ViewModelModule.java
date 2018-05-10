package com.stoyan.weatherful.dependency_injection.modules;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.stoyan.weatherful.ui.add_location_activity.AddLocationViewModel;
import com.stoyan.weatherful.viewmodel.ViewModelFactory;
import com.stoyan.weatherful.ui.day_forecast_fragment.DayForecastFragmentViewModel;
import com.stoyan.weatherful.ui.forecast_activity.ForecastActivityViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AddLocationViewModel.class)
    public abstract ViewModel bindAddLocationViewModel(AddLocationViewModel addLocationViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ForecastActivityViewModel.class)
    public abstract ViewModel bindForecastActivityViewModel(ForecastActivityViewModel forecastActivityViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DayForecastFragmentViewModel.class)
    public abstract ViewModel bindDayForecastFragmentViewModel(DayForecastFragmentViewModel dayForecastFragmentViewModel);

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
