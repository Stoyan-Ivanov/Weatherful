package com.stoyan.weatherful.locations_provider;

import com.stoyan.weatherful.Constants;
import com.stoyan.weatherful.models.Location;
import com.stoyan.weatherful.network.WeatherfulApplication;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

import static io.paperdb.Paper.book;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class LocationsProvider implements LocationsProviderContract {

    public LocationsProvider() {
        Paper.init(WeatherfulApplication.getStaticContext());
    }

    @Override
    public ArrayList<Location> getLocations() {
        ArrayList<Location> locations = new ArrayList<>();

        locations.add(new Location("Sofia", "Bulgaria", 42.69751, 23.32415));

        List<String> allKeys= Paper.book().getAllKeys();

        if(!allKeys.isEmpty()) {
            for (String key : allKeys) {
                locations.add((Location) Paper.book().read(key));
            }
        }

        return locations;
    }

    @Override
    public boolean saveLocation(Location location) {
        if(!checkIfLocationExists(location)) {
            book().write(location.getLocationName() + location.getCountry(), location);
            return true;
        } else {
            WeatherfulApplication.showToast(Constants.DUPLICATION_WHEN_ADDING_TOAST);
            return false;
        }
    }

    public void deleteLocation(Location location) {
        Paper.book().delete(location.getLocationName() + location.getCountry());
        WeatherfulApplication.showToast(Constants.SUCCESSFUL_DELETING);
    }

    private boolean checkIfLocationExists(Location location) {
        if(book().contains(location.getLocationName() + location.getCountry())) {
            return true;
        }
        return false;
    }
}
