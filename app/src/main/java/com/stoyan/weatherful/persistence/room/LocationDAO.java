package com.stoyan.weatherful.persistence.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.stoyan.weatherful.persistence.models.Location;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * Created by stoyan.ivanov2 on 4/19/2018.
 */

@Dao
public interface LocationDAO {

    @Query("SELECT * FROM locations")
    Single<List<Location>> getAllLocations();

    @Query("SELECT * FROM locations WHERE locationName = :name AND locationCountry = :country")
    Maybe<Location> getLocationByName(String name, String country);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMultipleLocations(ArrayList<Location> locations);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Location location);

    @Update(onConflict = OnConflictStrategy.ROLLBACK)
    void update(Location location);

    @Delete
    void delete(Location location);
}
