package com.stoyan.weatherful.persistence;

import android.content.Context;

import com.stoyan.weatherful.R;
import com.stoyan.weatherful.WeatherfulApplication;
import com.stoyan.weatherful.persistence.models.Location;
import com.stoyan.weatherful.persistence.room.LocationDAO;
import com.stoyan.weatherful.rx.RxUtils;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Stoyan on 27.1.2018 г..
 */

@Singleton
public class LocationsProvider implements LocationsProviderContract {
    private final LocationDAO mDAO;
    private Context mContext;

    @Inject
    public LocationsProvider(LocationDAO dao) {
        this.mContext = WeatherfulApplication.getStaticContext();
        this.mDAO = dao;
    }

    @Override
    public ArrayList<Location> getLocations() {
        ArrayList<Location>  locations = (ArrayList<Location>) mDAO.getAllLocations();

        if(locations.isEmpty()) {
            locations = initDatabase();
        }

        return locations;
    }

    private ArrayList<Location> initDatabase() {
        ArrayList<Location> defaultLocations = new ArrayList<>();

        defaultLocations.add(new Location("Sofia", "Bulgaria",  42.698334,  23.319941));
        defaultLocations.add(new Location("Prague", "Czech Republic", 50.08804,14.42076));
        defaultLocations.add(new Location("Toronto", "Canada", 43.653908, -79.384293));

        mDAO.insertMultipleLocations(defaultLocations);

        return defaultLocations;
    }

    @Override
    public boolean saveLocation(Location location) {
        mDAO.getLocationByName(location.getLocationName(), location.getCountry())
                .compose(RxUtils.applySchedulersMaybe())
                .subscribe(locationFromDB -> {
                    if(locationFromDB == null) {
                        mDAO.insert(location);
                        WeatherfulApplication.showToast(mContext.getString(R.string.successful_adding));
                    } else{
                        WeatherfulApplication.showToast(mContext.getString(R.string.duplication_when_adding));
                    }
                });

        return true;
    }

    public void updateLocation(Location location) {
        if(location != null) {
            mDAO.update(location);
        }
    }

    public void deleteLocation(Location location) {
        mDAO.getLocationByName(location.getLocationName(), location.getCountry())
                .compose(RxUtils.applySchedulersMaybe())
                .subscribe(locationFromDB -> {
                    if(locationFromDB != null) {
                        mDAO.delete(location);
                        WeatherfulApplication.showToast(mContext.getString(R.string.successful_deleting));
                    }
                });
    }
}
