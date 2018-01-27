package com.stoyan.weatherful.network;

import com.stoyan.weatherful.models.Location;
import com.stoyan.weatherful.view_utils.recyclerview_utils.locations_recyclerview.LocationViewHolder;
import com.stoyan.weatherful.view_utils.recyclerview_utils.locations_recyclerview.LocationsRecyclerviewAdapter;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public interface WeatherfulAPIImplContract {
    void getLocationImageUrl(LocationViewHolder viewHolder, Location locaton);
}
