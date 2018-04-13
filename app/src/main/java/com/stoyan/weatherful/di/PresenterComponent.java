package com.stoyan.weatherful.di;

import com.stoyan.weatherful.ui.add_location_activity.AddLocationActivityPresenter;
import com.stoyan.weatherful.ui.day_forecast_fragment.DayForecastFragmentPresenter;
import com.stoyan.weatherful.ui.forecast_activity.ForecastActivityPresenter;
import com.stoyan.weatherful.ui.forecast_pager_activity.ForecastPagerActivityPresenter;
import com.stoyan.weatherful.ui.location_activity.LocationActivityPresenter;

import dagger.Subcomponent;

/**
 * Copyright (c) 2018 Adastra.ONE a.s., all rights reserved.
 */
@Subcomponent(modules = {PresenterModule.class})
public interface PresenterComponent {

    void inject(LocationActivityPresenter locationActivityPresenter);

    void inject(ForecastActivityPresenter forecastActivityPresenter);

    void inject(ForecastPagerActivityPresenter forecastPagerActivityPresenter);

    void inject(AddLocationActivityPresenter addLocationActivityPresenter);

    void inject(DayForecastFragmentPresenter dayForecastFragmentPresenter);
}
