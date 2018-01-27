package com.stoyan.weatherful.location_activity;

import com.stoyan.weatherful.location_activity.locations_provider.LocationsProvider;
import com.stoyan.weatherful.models.Location;
import com.stoyan.weatherful.view_utils.recyclerview_utils.locations_recyclerview.LocationsRecyclerviewAdapter;
import com.stoyan.weatherful.view_utils.recyclerview_utils.locations_recyclerview.OnItemClickListener;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class LocationActivityProvider implements LocationActivityContract{
    LocationsProvider locationsProvider;

    public LocationActivityProvider() {
        locationsProvider = new LocationsProvider();
    }

    @Override
    public LocationsRecyclerviewAdapter getAdapter() {
        return new LocationsRecyclerviewAdapter(locationsProvider.getLocations(), new OnItemClickListener() {
            @Override
            public void OnItemClick(Location location) {
                startActivity();
            }
        });
    }

    @Override
    public void startActivity() {

    }
}
