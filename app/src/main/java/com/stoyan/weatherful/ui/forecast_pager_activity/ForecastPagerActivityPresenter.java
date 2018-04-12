package com.stoyan.weatherful.ui.forecast_pager_activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import com.stoyan.weatherful.Constants;
import com.stoyan.weatherful.ui.day_forecast_fragment.DayForecastFragment;
import com.stoyan.weatherful.db.Location;
import com.stoyan.weatherful.network.models.forecast_full_models.Data;

import java.util.ArrayList;

/**
 * Created by Stoyan on 28.1.2018 г..
 */

public class ForecastPagerActivityPresenter {
    private ArrayList<Fragment> fragments;
    private ArrayList<Data> data;
    private Location location;
    private int defaultPosition;

    private void getExtras(Intent intent) {
        data = intent.getParcelableArrayListExtra(Constants.EXTRA_DATA);
        location = intent.getParcelableExtra(Constants.EXTRA_LOCATION);
        defaultPosition = intent.getIntExtra(Constants.EXTRA_POSITION, 0);
    }

    public void setExtras(Intent intent) {
        getExtras(intent);
    }

    public String getHeader() {
        if(location != null) {
            return location.toString();
        } else {
            throw new NullPointerException();
        }
    }

    public int getOffScreenLimit() {
        return fragments.isEmpty() ? 0: fragments.size() / 2;
    }


    public int getDefaultPosition() {
        return defaultPosition;
    }

    public ArrayList<Fragment> getFragments() {
        fragments = new ArrayList<>();

        for(Data singleDataElement: data) {
            fragments.add(getNewFragment(singleDataElement));
        }

        return fragments;
    }

    private Fragment getNewFragment(Data singleDataElement) {
        return DayForecastFragment.newInstance(singleDataElement);
    }
}
