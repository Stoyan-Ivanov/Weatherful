package com.stoyan.weatherful.ui.forecast_pager_activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import com.stoyan.weatherful.Constants;
import com.stoyan.weatherful.ui.day_forecast_fragment.DayForecastFragment;
import com.stoyan.weatherful.db.Location;
import com.stoyan.weatherful.network.models.forecast_full_models.Data;
import com.stoyan.weatherful.view_utils.CustomPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Stoyan on 28.1.2018 г..
 */

public class ForecastPagerActivityPresenter implements ForecastPagerActivityContract{
    private ForecastPagerActivity forecastPagerActivity;
    private ArrayList<Fragment> fragments;
    private ArrayList<Data> data;
    private Location location;
    private int defaultPosition;

    public ForecastPagerActivityPresenter(Intent intent, ForecastPagerActivity forecastPagerActivity) {
        getExtras(intent);
        this.forecastPagerActivity = forecastPagerActivity;
    }

    @Override
    public void getExtras(Intent intent) {
        data = intent.getParcelableArrayListExtra(Constants.EXTRA_DATA);
        location = intent.getParcelableExtra(Constants.EXTRA_LOCATION);
        defaultPosition = intent.getIntExtra(Constants.EXTRA_POSITION, 0);
    }

    @Override
    public String getHeader() {
        return location.getLocationName() + ", " + location.getCountry();
    }

    @Override
    public CustomPagerAdapter getPagerAdapter() {
        fragments = getFragments();

        return new CustomPagerAdapter(forecastPagerActivity.getSupportFragmentManager(), fragments);
    }

    @Override
    public int getOffScreenLimit() {
        return fragments.isEmpty() ? 0: 3;
    }

    @Override
    public int getDefaultPosition() {
        return defaultPosition;
    }

    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();

        for(Data singleDataElement: data) {
            fragments.add(getNewFragment(singleDataElement));
        }

        return fragments;
    }

    private Fragment getNewFragment(Data singleDataElement) {
        return DayForecastFragment.newInstance(singleDataElement);
    }
}
