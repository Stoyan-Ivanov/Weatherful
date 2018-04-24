package com.stoyan.weatherful.persistence.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.stoyan.weatherful.persistence.models.Location;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;

/**
 * Created by stoyan.ivanov2 on 4/19/2018.
 */

@Dao
public interface LocationDAO {

    @Query("SELECT * FROM locations")
    Maybe<List<Location>> getAllLocations();

    @Query("SELECT * FROM locations WHERE locationName = :locationName AND locationCountry = :locationCountry")
    Maybe<Location> getLocationByName(String locationName, String locationCountry);

    @Insert
    void insertMultipleLocations(ArrayList<Location> locations);

    @Insert
    void insert(Location location);

    @Update
    void update(Location location);

    @Delete
    void delete(Location location);
}
