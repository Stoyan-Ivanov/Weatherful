package com.stoyan.weatherful.db.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.stoyan.weatherful.db.models.Location;

import javax.inject.Singleton;

/**
 * Created by stoyan.ivanov2 on 4/19/2018.
 */

@Singleton
@Database(entities = {Location.class}, version = 1, exportSchema = false)
public abstract class LocationsDatabase extends RoomDatabase {

    public abstract LocationDAO locationDAO();
}
