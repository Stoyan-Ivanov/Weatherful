package com.stoyan.weatherful.persistence;

import android.content.Context;

import com.stoyan.weatherful.R;
import com.stoyan.weatherful.WeatherfulApplication;
import com.stoyan.weatherful.persistence.models.Location;
import com.stoyan.weatherful.persistence.room.LocationDAO;
import com.stoyan.weatherful.rx.RxUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;

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
    public Observable<List<Location>> getLocations() {
        Single<List<Location>> single= mDAO.getAllLocations().compose(RxUtils.applySchedulerSingle());
        return single.toObservable();
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
    public void saveLocation(Location location) {
        new Thread(() -> mDAO.insert(location)).start();

    }

    public void updateLocation(Location location) {
        if(location != null) {
            new Thread(() -> mDAO.update(location)).start();
        }
    }

    public void deleteLocation(Location location) {
        new Thread(() ->  mDAO.delete(location)).start();
        WeatherfulApplication.showToast(mContext.getString(R.string.successful_deleting));
    }
}
