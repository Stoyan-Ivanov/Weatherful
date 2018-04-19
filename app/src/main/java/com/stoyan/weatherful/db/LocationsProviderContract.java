package com.stoyan.weatherful.db;

import com.stoyan.weatherful.db.models.Location;

import java.util.ArrayList;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public interface LocationsProviderContract {
    ArrayList<Location> getLocations();

    boolean saveLocation(Location location);
}
