package com.stoyan.weatherful.location_activity;

import com.stoyan.weatherful.view_utils.recyclerview_utils.locations_recyclerview.LocationsRecyclerviewAdapter;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public interface LocationActivityContract {

    LocationsRecyclerviewAdapter getAdapter();

    String getHeader();
}
