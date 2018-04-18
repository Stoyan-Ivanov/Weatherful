package com.stoyan.weatherful.ui.location_activity;

import com.stoyan.weatherful.db.Location;
import com.stoyan.weatherful.ui.base_ui.contract.BaseViewContract;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public interface LocationActivityContract extends BaseViewContract {
    void notifyDataSetChanged();
    void showError(Throwable throwable);
    void startNewActivity();
    void showNoInternetView();
    void loadCurrentLocation();
    void startNewForecastActivity(Location location);
}
