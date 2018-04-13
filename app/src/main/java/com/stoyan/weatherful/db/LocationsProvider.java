package com.stoyan.weatherful.db;

import android.util.Log;

import com.stoyan.weatherful.R;
import com.stoyan.weatherful.WeatherfulApplication;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.paperdb.Paper;

import static io.paperdb.Paper.book;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

@Singleton
public class LocationsProvider implements LocationsProviderContract {

    @Inject
    public LocationsProvider() {}

    @Override
    public ArrayList<Location> getLocations() {

        ArrayList<Location> locations = new ArrayList<>();

        List<String> allKeys= Paper.book().getAllKeys();

        if(!allKeys.isEmpty()) {
            for (String key : allKeys) {
                locations.add(Paper.book().read(key));
            }
        } else {
            locations = initDatabase();
        }

        Log.d("SII", "getLocations: " + locations.toString());
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
        if(location != null) {
            writeToDatabase(location);
        }
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
