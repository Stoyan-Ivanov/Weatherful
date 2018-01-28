package com.stoyan.weatherful.forecast_pager_activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;

/**
 * Created by Stoyan on 28.1.2018 г..
 */

public class ForecastPagerActivityPresenter implements ForecastPagerActivityContract{

    public ForecastPagerActivityPresenter(Intent intent) {
        getExtras(intent);
    }

    @Override
    public ViewPager getViewPager() {
        return null;
    }

    @Override
    public void getExtras(Intent intent) {

    }
}
