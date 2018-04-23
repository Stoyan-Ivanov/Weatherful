package com.stoyan.weatherful.db.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.stoyan.weatherful.WeatherfulApplication;
import com.stoyan.weatherful.db.models.Location;

/**
 * Created by stoyan.ivanov2 on 4/19/2018.
 */

@Database(entities = {Location.class}, version = 1, exportSchema = false)
public abstract class LocationsDatabase extends RoomDatabase {
    private static final String DB_NAME = "locationsDatabase.db";
    private static LocationsDatabase INSTANCE;

    public static LocationsDatabase getInstance() {
        if(INSTANCE == null) {
            create();
        }
        return INSTANCE;
    }

    private static void create() {
        Room.databaseBuilder(WeatherfulApplication.getStaticContext(), LocationsDatabase.class, DB_NAME).build();
    }

    public abstract LocationDAO locationDA0();
}
