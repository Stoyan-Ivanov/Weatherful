package com.stoyan.weatherful.locations_provider;

import android.view.Gravity;
import android.widget.Toast;

import com.stoyan.weatherful.Constants;
import com.stoyan.weatherful.models.Location;
import com.stoyan.weatherful.network.WeatherfulAPIImpl;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

import static io.paperdb.Paper.book;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

public class LocationsProvider implements LocationsProviderContract {

    public LocationsProvider() {
        Paper.init(WeatherfulAPIImpl.getStaticContext());
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
            showDuplicationToast();
            return false;
        }
    }

    private boolean checkIfLocationExists(Location location) {
        if(book().contains(location.getLocationName() + location.getCountry())) {
            return true;
        }
        return false;
    }

    private void showDuplicationToast() {
        Toast toast=Toast.makeText(WeatherfulAPIImpl.getStaticContext(),
                Constants.DUPLICATION_WHEN_ADDING_TOAST,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 100);
        toast.show();
    }
}
