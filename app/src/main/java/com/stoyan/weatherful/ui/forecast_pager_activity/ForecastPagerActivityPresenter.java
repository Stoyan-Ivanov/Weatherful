package com.stoyan.weatherful.ui.forecast_pager_activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import com.stoyan.weatherful.Constants;
import com.stoyan.weatherful.ui.base_ui.presenter.BasePresenter;
import com.stoyan.weatherful.ui.day_forecast_fragment.DayForecastFragment;
import com.stoyan.weatherful.db.models.Location;
import com.stoyan.weatherful.network.models.forecast_full_models.Data;

import java.util.ArrayList;

/**
 * Created by Stoyan on 28.1.2018 г..
 */

public class ForecastPagerActivityPresenter extends BasePresenter {
    private ArrayList<Fragment> mFragments;
    private ArrayList<Data> mData;
    private Location mLocation;
    private int mDefaultPosition;

    @Override
    protected void inject() {
        getPresenterComponent().inject(this);
    }

    private void getExtras(Intent intent) {
        mData = intent.getParcelableArrayListExtra(Constants.EXTRA_DATA);
        mLocation = intent.getParcelableExtra(Constants.EXTRA_LOCATION);
        mDefaultPosition = intent.getIntExtra(Constants.EXTRA_POSITION, 0);
    }

    public void setExtras(Intent intent) {
        getExtras(intent);
    }

    public String getHeader() {
        if(mLocation != null) {
            return mLocation.toString();
        } else {
            throw new NullPointerException();
        }
    }

    public int getOffScreenLimit() {
        return mFragments.isEmpty() ? 0: mFragments.size() / 2;
    }


    public int getDefaultPosition() {
        return mDefaultPosition;
    }

    public ArrayList<Fragment> getFragments() {
        mFragments = new ArrayList<>();

        for(Data singleDataElement: mData) {
            mFragments.add(getNewFragment(singleDataElement));
        }

        return mFragments;
    }

    private Fragment getNewFragment(Data singleDataElement) {
        return DayForecastFragment.newInstance(singleDataElement);
    }
}
