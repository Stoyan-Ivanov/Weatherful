package com.stoyan.weatherful.ui.location_activity.locations_provider;

import com.stoyan.weatherful.models.Location;

import java.util.ArrayList;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class LocationsProvider implements LocationsProviderContract {

    @Override
    public ArrayList<Location> getLocations() {
        ArrayList<Location> locations = new ArrayList<>();

        locations.add(new Location("Sofia", "Bulgaria", 42.69751, 23.32415));

        return locations;
    }
}
