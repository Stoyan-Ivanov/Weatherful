package com.stoyan.weatherful.db.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.stoyan.weatherful.db.models.Location;

import java.util.List;

/**
 * Created by stoyan.ivanov2 on 4/19/2018.
 */

@Dao
public interface LocationDAO {

    @Query("SELECT * FROM location")
    List<Location> getAllLocations();

    @Insert
    void insert(Location location);

    @Update
    void update(Location location);

    @Delete
    void delete(Location location);
}
