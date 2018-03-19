package com.stoyan.weatherful.ui.add_location_activity;


import com.stoyan.weatherful.ui.BasePresenterContract;

/**
 * Created by Stoyan on 28.1.2018 г..
 */

public interface AddLocationActivityContract  extends BasePresenterContract{
    void addNewLocation(String cityName, String countryName);
}
