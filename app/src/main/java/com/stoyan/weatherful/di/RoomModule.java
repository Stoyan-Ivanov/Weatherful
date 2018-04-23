package com.stoyan.weatherful.di;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.stoyan.weatherful.db.room.LocationDAO;
import com.stoyan.weatherful.db.room.LocationsDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by stoyan.ivanov2 on 4/23/2018.
 */

@Module
public class RoomModule {
    private LocationsDatabase mDatabase;

    public RoomModule(Application mApplication) {
        mDatabase = Room.databaseBuilder(mApplication, LocationsDatabase.class, "locationsDatabase.db").allowMainThreadQueries().build();
    }

    @Singleton
    @Provides
    LocationsDatabase providesRoomDatabase() {
        return mDatabase;
    }

    @Singleton
    @Provides
    LocationDAO providesProductDao(LocationsDatabase database) {
        return database.locationDAO();
    }

}
