package com.stoyan.weatherful.persistence;

import com.stoyan.weatherful.persistence.models.Location;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by Stoyan on 27.1.2018 г..
 */

public interface LocationsProviderContract {
    Observable<List<Location>> getLocations();

    void saveLocation(Location location);
}
