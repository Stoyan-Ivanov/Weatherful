package com.stoyan.weatherful.ui.forecast_pager_activity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.stoyan.weatherful.Constants;
import com.stoyan.weatherful.network.models.forecast_full_models.Data;
import com.stoyan.weatherful.persistence.models.Location;
import com.stoyan.weatherful.ui.day_forecast_fragment.DayForecastFragment;
import com.stoyan.weatherful.viewmodel.BaseViewModel;

import java.util.ArrayList;

public class ForecastPagerActivityViewModel extends BaseViewModel {
    private ArrayList<Fragment> mFragments;
    private MutableLiveData<ArrayList<Data>> mData;
    private MutableLiveData<Location> mLocation;
    private int mDefaultPosition;
    private int DEFAULT_VIEWPAGER_OFFSET_SCREEN_LIMIT = 3;

    @Override
    protected void inject() {
        getViewModelComponent().inject(this);
    }

    public ForecastPagerActivityViewModel() {
        mFragments = new ArrayList<>();
        mData = new MutableLiveData<>();
        mLocation = new MutableLiveData<>();
    }

    public void setExtras(Intent intent) {
        mData.setValue(intent.getParcelableArrayListExtra(Constants.EXTRA_DATA));
        mLocation.setValue(intent.getParcelableExtra(Constants.EXTRA_LOCATION));
        mDefaultPosition = intent.getIntExtra(Constants.EXTRA_POSITION, 0);
    }

    public LiveData<String> getHeader() {
            return Transformations.map(mLocation, location -> location.toString());
    }

    public LiveData<ArrayList<Data>> getData() {
        return mData;
    }

    public int getOffScreenLimit() {
        return mFragments.isEmpty() ? 0: DEFAULT_VIEWPAGER_OFFSET_SCREEN_LIMIT;
    }


    public int getDefaultPosition() {
        return mDefaultPosition;
    }

    public ArrayList<Fragment> getFragments(ArrayList<Data> data) {
        for(Data singleDataElement: data) {
            mFragments.add(DayForecastFragment.newInstance(singleDataElement));
        }

        return mFragments;
    }
}
