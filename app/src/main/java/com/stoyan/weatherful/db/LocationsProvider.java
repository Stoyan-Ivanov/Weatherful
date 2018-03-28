package com.stoyan.weatherful.db;

import com.stoyan.weatherful.Constants;
import com.stoyan.weatherful.R;
import com.stoyan.weatherful.network.WeatherfulApplication;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

import static io.paperdb.Paper.book;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class LocationsProvider implements LocationsProviderContract {
    private static LocationsProvider instance;

    private LocationsProvider() {
        Paper.init(WeatherfulApplication.getStaticContext());
    }

    public static LocationsProvider getInstance() {
        if(instance == null) {
            instance = new LocationsProvider();
        }
        return instance;
    }

    @Override
    public ArrayList<Location> getLocations() {
        ArrayList<Location> locations = new ArrayList<>();

        List<String> allKeys= Paper.book().getAllKeys();

        if(!allKeys.isEmpty()) {
            for (String key : allKeys) {
                locations.add((Location) Paper.book().read(key));
            }
        } else {
            locations = initDatabase();
        }

        return locations;
    }

    private ArrayList<Location> initDatabase() {
        ArrayList<Location> defaultLocations = new ArrayList<>();

        defaultLocations.add(new Location("Sofia", "Bulgaria",  42.698334,  23.319941));
        defaultLocations.add(new Location("Prague", "Czech Republic", 50.08804,14.42076));
        defaultLocations.add(new Location("Toronto", "Canada", 43.653908, -79.384293));

        for(Location location: defaultLocations) {
           saveLocation(location);
        }

        return defaultLocations;
    }

    @Override
    public boolean saveLocation(Location location) {
        if(!checkIfLocationExists(location)) {
            writeToDatabase(location);
            return true;
        } else {
            WeatherfulApplication.showToast(
                    WeatherfulApplication.getStringFromId(R.string.duplication_when_adding));
            return false;
        }
    }

    public void updateLocation(Location location) {
       writeToDatabase(location);
    }

    private void writeToDatabase(Location location) {
        book().write(location.getLocationName() + location.getCountry(), location);
    }

    public void deleteLocation(Location location) {
        Paper.book().delete(location.getLocationName() + location.getCountry());
        WeatherfulApplication.showToast(
                WeatherfulApplication.getStringFromId(R.string.successful_deleting));
    }

    private boolean checkIfLocationExists(Location location) {
        if(book().contains(location.getLocationName() + location.getCountry())) {
            return true;
        }
        return false;
    }
}
