package com.stoyan.weatherful.location_activity;

import com.stoyan.weatherful.models.Location;

import java.util.ArrayList;

/**
 * Created by Stoya on 27.1.2018 г..
 */

public class DataProvider {
    public ArrayList<Location> getData() {
        ArrayList<Location> locations = new ArrayList<>();

        locations.add(new Location("Sofia", "Bulgaria", 42.69751, 23.32415));

        return locations;
    }
}
