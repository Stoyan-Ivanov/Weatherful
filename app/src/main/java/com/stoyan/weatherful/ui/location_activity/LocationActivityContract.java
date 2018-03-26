package com.stoyan.weatherful.ui.location_activity;

import com.stoyan.weatherful.ui.BasePresenterContract;
import com.stoyan.weatherful.view_utils.recyclerview_utils.locations_recyclerview.LocationsRecyclerViewAdapter;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public interface LocationActivityContract {
    void notifyDataSetChanged();
    void showError(Throwable throwable);
    void startNewActivity();
}
