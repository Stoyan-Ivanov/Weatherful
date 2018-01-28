package com.stoyan.weatherful.locations_provider;

import com.stoyan.weatherful.models.Location;

import java.util.ArrayList;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public interface LocationsProviderContract {
    ArrayList<Location> getLocations();

    boolean saveLocation(Location location);
}
