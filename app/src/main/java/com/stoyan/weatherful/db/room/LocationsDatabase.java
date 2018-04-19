package com.stoyan.weatherful.db.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.stoyan.weatherful.WeatherfulApplication;
import com.stoyan.weatherful.db.models.Location;
import com.stoyan.weatherful.db.models.Converters;

/**
 * Created by stoyan.ivanov2 on 4/19/2018.
 */

@Database(entities = {Location.class}, version = 2)
@TypeConverters(Converters.class)
public abstract class LocationsDatabase extends RoomDatabase {
    private static final String DB_NAME = "locationsDatabase.db";
    private static LocationsDatabase sLocationsDatabase;

    public static LocationsDatabase getInstance() {
        if(sLocationsDatabase == null) {
            create();
        }
        return sLocationsDatabase;
    }

    private static void create() {
        Room.databaseBuilder(WeatherfulApplication.getStaticContext(), LocationsDatabase.class, DB_NAME).build();
    }


    public abstract LocationDAO locationDA0();

}
